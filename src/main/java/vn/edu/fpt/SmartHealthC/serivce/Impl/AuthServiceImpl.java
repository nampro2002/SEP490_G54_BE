package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.RefreshTokenResponseDto;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.*;
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public AuthenticationResponseDto login(LoginDto request) throws ParseException {
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

        //AccessToken
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", optionalUser.get().getId());
        var jwt = jwtProvider.generateToken(extraClaims, optionalUser.get());
        //RefreshToken
        String refreshToken = UUID.randomUUID().toString();
        if(checkRefreshTokenDuplicate(refreshToken) == true){
            refreshToken = UUID.randomUUID().toString();
        }
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresTime = now.plusMinutes(5);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatedDate = expiresTime.format(formatter);
        RefreshToken refreshTokenCreate = new RefreshToken().builder()
                .accessToken(jwt)
                .accessExpiryTime(jwtProvider.extractExpirationDate(jwt))
                .refreshToken(refreshToken)
                .refreshExpiryTime(formatDate.parse(stringFormatedDate))
                .accountId(optionalUser.get()).build();
        refreshTokenRepository.save(refreshTokenCreate);

        return AuthenticationResponseDto.builder()
                .type(optionalUser.get().getType())
                .idUser(optionalUser.get().getId())
                .accessToken(jwt)
                .refreshToken(refreshToken)
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
        String message = "Code xác thực email đăng ký của bạn là : " +codeVerify;

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

    @Override
    public RefreshTokenResponseDto refreshToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //Check refresh token
        Optional<RefreshToken> refreshTokenFilter = refreshTokenRepository.findRecordByReToken(refreshToken);
        if(refreshTokenFilter.isEmpty()) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_NOT_EXIST);
        }
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatedDate = now.format(formatter);
        //Check expires refresh token
        if (formatDate.parse(stringFormatedDate).after(formatDate.parse(refreshTokenFilter.get().getRefreshExpiryTime().toString()))) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        Optional<Account> optionalUser = accountRepository.findById(refreshTokenFilter.get().getAccountId().getId());
        if(optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        //Check token request và refresh có là cùng thuộc 1 người
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshTokenHeader;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        refreshTokenHeader = authHeader.substring(7);
        if(refreshTokenHeader != refreshTokenFilter.get().getAccessToken()){
            throw new AppException(ErrorCode.TOKEN_NOT_OWNED);
        }

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", optionalUser.get().getId());
        var jwt = jwtProvider.generateToken(extraClaims, optionalUser.get());
        String refreshTokenNewString = UUID.randomUUID().toString();
        if(checkRefreshTokenDuplicate(refreshTokenNewString) == true){
            refreshTokenNewString = UUID.randomUUID().toString();
        }
        // Lấy thời gian hiện tại
        LocalDateTime expiresTime = now.plusMinutes(5);
        String stringFormatedDateToken = expiresTime.format(formatter);
        RefreshToken refreshTokenUpdate = refreshTokenFilter.get();
        refreshTokenUpdate.setAccessToken(jwt);
        refreshTokenUpdate.setAccessExpiryTime(jwtProvider.extractExpirationDate(jwt));
        refreshTokenUpdate.setRefreshToken(refreshTokenNewString);
        refreshTokenUpdate.setAccountId(optionalUser.get());
        refreshTokenUpdate.setRefreshExpiryTime(formatDate.parse(stringFormatedDateToken));
        refreshTokenRepository.save(refreshTokenUpdate);

        return RefreshTokenResponseDto.builder()
                .accessToken(jwt)
                .refreshToken(refreshTokenNewString)
                .build();
    }
    private boolean checkRefreshTokenDuplicate(String refreshToken) {
        Optional<RefreshToken> refreshTokenFilter = refreshTokenRepository.findRecordByReToken(refreshToken);
        return refreshTokenFilter.isPresent() ? true :false;
    }


}
