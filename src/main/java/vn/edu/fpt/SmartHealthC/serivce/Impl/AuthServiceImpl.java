package vn.edu.fpt.SmartHealthC.serivce.Impl;

import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTopic;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DoctorRegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.NotificationSubscriptionRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.RefreshTokenResponseDto;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.*;
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final UserMedicalHistoryRepository userMedicalHistoryRepository;
    private final AppUserRepository appUserRepository;
    private final EmailService emailService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final NotificationService notificationService;
    private final WebUserRepository webUserRepository;
    private final CodeRepository codeRepository;

    @Override
    public AuthenticationResponseDto login(LoginDto request) throws ParseException {
        Optional<Account> optionalUser = accountRepository.findAccountByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.CREDENTIAL_INVALID);
        }
        if (optionalUser.get().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        if (optionalUser.get().isActive() == false) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_ACTIVATED);
        }
        Account existingUser = optionalUser.get();
        //check password
        if (!passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
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
        extraClaims.put("UniqueIdentifier", UUID.randomUUID().toString());
        var jwt = jwtProvider.generateToken(extraClaims, optionalUser.get());
        //RefreshToken
        String refreshToken = UUID.randomUUID().toString();
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresTime = now.plusDays(30);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatedDate = expiresTime.format(formatter);
        RefreshToken refreshTokenCreate = new RefreshToken().builder()
                .accessToken(jwt)
                .accessExpiryTime(jwtProvider.extractExpirationDate(jwt))
                .refreshToken(refreshToken)
                .refreshExpiryTime(formatDate.parse(stringFormatedDate))
                .accountId(optionalUser.get())
                .deviceToken(request.getDeviceToken())
                .language(request.getLanguage())
                .build();
        cleanRefreshToken(optionalUser.get().getId(), request.getDeviceToken());
        refreshTokenRepository.save(refreshTokenCreate);
        if (optionalUser.get().getType().equals(TypeAccount.USER) && optionalUser.get().isActive()) {
            unSubAll(refreshTokenCreate);
            notificationService.updateStatusNotification(request.getEmail(), request.getDeviceToken(), request.getLanguage());
        }
        return AuthenticationResponseDto.builder()
                .idUser(optionalUser.get().getId())
                .role(optionalUser.get().getType())
                .isActivated(optionalUser.get().isActive())
                .isDeleted(optionalUser.get().isDeleted())
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    public void cleanRefreshToken(Integer accountId, String deviceToken) throws ParseException {
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findRecordBydDeviceToken(deviceToken);
        for (RefreshToken refreshToken : refreshTokenList) {
            refreshTokenRepository.delete(refreshToken);
        }
    }

    @Override
    public void register(RegisterDto request) {
        Optional<Account> existingAccount = accountRepository.findByEmail(request.getEmail());

        if (existingAccount.isPresent()) {
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
                .gender(request.getGender())
                .height(request.getHeight())
                .weight(request.getWeight())
                .phoneNumber(request.getPhoneNumber())
                .cic(request.getCic())
                .build();
        newAppUserInfo = appUserRepository.save(newAppUserInfo);
        for (Integer i : request.getListMedicalHistory()) {
            MedicalHistory medicalHistory = medicalHistoryRepository.findById(i)
                    .orElseThrow();
            UserMedicalHistory userMedicalHistory = UserMedicalHistory
                    .builder()
                    .appUserId(newAppUserInfo)
                    .conditionId(medicalHistory)
                    .build();
            userMedicalHistoryRepository.save(userMedicalHistory);
        }
    }

    @Override
    public void registerDoctor(DoctorRegisterDto request) {
        Optional<Account> existingAccount = accountRepository.findByEmail(request.getEmail());

        if (existingAccount.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Account newAccount = Account.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .type(TypeAccount.DOCTOR)
                .isActive(false)
                .build();
        newAccount = accountRepository.save(newAccount);
        WebUser newWebUserInfo = WebUser.builder()
                .accountId(newAccount)
                .userName(request.getName())
                .dob(request.getDob())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .build();
        newWebUserInfo = webUserRepository.save(newWebUserInfo);
    }

    @Override
    public void logout(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String accessToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        accessToken = authHeader.substring(7);
        Optional<RefreshToken> refreshTokenFilter = refreshTokenRepository.findRecordByAcToken(accessToken);
        if (refreshTokenFilter.isEmpty()) {
            return;
        }
        RefreshToken refreshToken = refreshTokenFilter.get();
        refreshTokenRepository.delete(refreshToken);
        unSubAll(refreshToken);

    }

    public void unSubAll(RefreshToken refreshToken) {
        try {
//            unSubTopic(refreshToken.getDeviceToken(), refreshToken.getLanguage().equals(TypeLanguage.EN) ? TypeTopic.DAILY_EN.getTopicName() : TypeTopic.DAILY_KR.getTopicName());
//            unSubTopic(refreshToken.getDeviceToken(), refreshToken.getLanguage().equals(TypeLanguage.EN) ? TypeTopic.MONDAY_AM_EN.getTopicName() : TypeTopic.MONDAY_AM_KR.getTopicName());
//            unSubTopic(refreshToken.getDeviceToken(), refreshToken.getLanguage().equals(TypeLanguage.EN) ? TypeTopic.SUNDAY_PM_EN.getTopicName() : TypeTopic.SUNDAY_PM_KR.getTopicName());
            unSubTopic(refreshToken.getDeviceToken(), TypeTopic.DAILY_EN.getTopicName());
            unSubTopic(refreshToken.getDeviceToken(), TypeTopic.MONDAY_AM_EN.getTopicName());
            unSubTopic(refreshToken.getDeviceToken(), TypeTopic.SUNDAY_PM_EN.getTopicName());
            unSubTopic(refreshToken.getDeviceToken(), TypeTopic.DAILY_KR.getTopicName());
            unSubTopic(refreshToken.getDeviceToken(), TypeTopic.MONDAY_AM_KR.getTopicName());
            unSubTopic(refreshToken.getDeviceToken(), TypeTopic.SUNDAY_PM_KR.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("FirebaseMessagingException", e);
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }


    public void unSubTopic(String deviceToken, String topicName) throws FirebaseMessagingException {
        NotificationSubscriptionRequest notificationSubscriptionRequest = NotificationSubscriptionRequest.builder().deviceToken(deviceToken).topicName(topicName).build();
        notificationService.unsubscribeDeviceFromTopic(notificationSubscriptionRequest);
    }

    @Override
    public Boolean checkRegisterEmail(String email, String code) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        Optional<Code> codeRegister = codeRepository.findByEmailAndCode(email, code);
        if (codeRegister.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkRefreshToken(String token, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //Check refresh token
        Optional<RefreshToken> refreshTokenFilter = refreshTokenRepository.findRecordByReToken(token);
        if (refreshTokenFilter.isEmpty()) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatedDate = now.format(formatter);
        //Check expires refresh token
        if (formatDate.parse(stringFormatedDate).after(formatDate.parse(refreshTokenFilter.get().getRefreshExpiryTime().toString()))) {
            return true;
        }
        return false;
    }

    @Override
    public String sendEmailCode(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        String codeVerify = emailService.generateRandomCode(6);
        Code code = new Code().builder()
                .email(email).code(codeVerify).build();
        codeRepository.save(code);
        String message = "Verify code for your email is : " + codeVerify;
        boolean result = emailService.sendMail(
                email,
                "Verify code for your email",
                message
        );
        if (result == false) {
            throw new AppException(ErrorCode.SEND_EMAIL_FAIL);
        }
        return "Send code success";
    }

    @Override
    public RefreshTokenResponseDto refreshToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //Check refresh token
        Optional<RefreshToken> refreshTokenFilter = refreshTokenRepository.findRecordByReToken(refreshToken);
        if (refreshTokenFilter.isEmpty()) {
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
        if (optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        //Check token request và refresh có là cùng thuộc 1 người
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshTokenHeader;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        refreshTokenHeader = authHeader.substring(7);
        if (!refreshTokenHeader.equals(refreshTokenFilter.get().getAccessToken())) {
            throw new AppException(ErrorCode.TOKEN_NOT_OWNED);
        }

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("UniqueIdentifier", UUID.randomUUID().toString());
        extraClaims.put("role", optionalUser.get().getType().name());
        var jwt = jwtProvider.generateToken(extraClaims, optionalUser.get());
        String refreshTokenNewString = UUID.randomUUID().toString();
        // Lấy thời gian hiện tại
        LocalDateTime expiresTime = now.plusDays(30);
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


}
