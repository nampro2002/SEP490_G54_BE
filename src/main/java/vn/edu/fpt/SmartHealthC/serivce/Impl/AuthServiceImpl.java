package vn.edu.fpt.SmartHealthC.serivce.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public AuthenticationResponseDto login(LoginDto request){
        Optional<Account> optionalUser = accountRepository.findByEmail(request.getEmail());
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
                .token(jwt)
                .build();
    }

    @Override
    public AuthenticationResponseDto register(RegisterDto request) {
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
                .hospitalNumber(request.getHospitalNumber())
                .build();
        appUserRepository.save(newAppUserInfo);
        var jwt = jwtProvider.generateToken(newAccount);
        return AuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }
}
