package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final UserMedicalHistoryRepository userMedicalHistoryRepository;
    private final AppUserRepository appUserRepository;
    private final EmailService  emailService;

    @Override
    public AuthenticationResponseDto login(LoginDto request){
        Optional<Account> optionalUser = accountRepository.findAccountByEmail(request.getEmail());
        if(optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.CREDENTIAL_INVALID);
        }
        Account existingUser = optionalUser.get();
        //check password
        if(!passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
            throw new AppException(ErrorCode.CREDENTIAL_INVALID);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwt = jwtProvider.generateToken(optionalUser.get());
        return AuthenticationResponseDto.builder()
                .type(optionalUser.get().getType())
                .idUser(optionalUser.get().getId())
                .token(jwt)
                .build();
    }

    @Override
    public void register(RegisterDto request) {
        Optional<Account> existingAccount = accountRepository.findByEmail(request.getEmail());

        if(existingAccount.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Account newAccount = Account.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .type(TypeAccount.USER)
                .isActive(false)
                .build();
        newAccount = accountRepository.save(newAccount);
        AppUser newAppUserInfo = AppUser.builder()
                .accountId(newAccount)
                .name(request.getName())
                .dob(request.getDob())
                .gender(request.isGender())
                .height(request.getHeight())
                .weight(request.getWeight())
                .phoneNumber(request.getPhoneNumber())
                .cic(request.getCic())
                .build();
        newAppUserInfo = appUserRepository.save(newAppUserInfo);
//        for(Integer i : request.getListMedicalHistory()){
//            MedicalHistory medicalHistory = medicalHistoryRepository.findById(i)
//                    .orElseThrow();
//            UserMedicalHistory userMedicalHistory  = UserMedicalHistory
//                    .builder()
//                    .appUserId(newAppUserInfo)
//                    .conditionId(medicalHistory)
//                    .build();
//            userMedicalHistoryRepository.save(userMedicalHistory);
//        }
    }

    @Override
    public String sendEmailCode(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        String codeVerify = emailService.generateRandomCode(6);
        String message = "Code xác thực email của bạn là : " +codeVerify;

        boolean result =  emailService.sendMail(
                email,
                "MÃ XÁC THỰC EMAIL",
                message
        );
        if(result == false){
            throw new AppException(ErrorCode.SEND_EMAIL_FAIL);
        }
        return codeVerify;
    }
}
