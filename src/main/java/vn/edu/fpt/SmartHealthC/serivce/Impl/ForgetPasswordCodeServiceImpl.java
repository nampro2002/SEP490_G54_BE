package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ChangePasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.CodeRepository;
import vn.edu.fpt.SmartHealthC.repository.ForgetPasswordCodeRepository;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ForgetPasswordCodeServiceImpl implements ForgetPasswordCodeService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private ForgetPasswordCodeRepository forgetPasswordCodeRepository;
    @Autowired
    private CodeRepository codeRepository;

    @Override
    public String sendEmailCode(String email) throws ParseException {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);
        }
        String codeVerify = emailService.generateRandomCode(6);
        String message = "Code xác thực quên mật khẩu của bạn là : " +codeVerify;

       boolean result =  emailService.sendMail(
               email,
                "Mã xác thực ",
                message
        );
       if(result == false){
           throw new AppException(ErrorCode.SEND_EMAIL_FAIL);
       }
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresTime = now.plusMinutes(5);

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatedDate = expiresTime.format(formatter);

        ForgetPasswordCode forgetPasswordCode =ForgetPasswordCode.builder()
                .code(codeVerify)
                .accountId(account.get())
                .expiredDate(formatDate.parse(stringFormatedDate)).build();
        forgetPasswordCodeRepository.save(forgetPasswordCode);
        return "Gửi mã xác thức quên mật khẩu thành công";
    }

    @Override
    public boolean verifyAndChangePassword(ChangePasswordCodeDTO changePasswordCodeDTO) throws ParseException {
        Optional<Account> account = accountRepository.findByEmail(changePasswordCodeDTO.getEmail());
        if (account.isEmpty()) {
//            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);
            return false;
        }
        account.get().setPassword(passwordEncoder.encode(changePasswordCodeDTO.getPassword()));
        accountRepository.save(account.get());
        return true;
    }

    @Override
    public String sendPasswordEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);
        }
        String newPassword = generateRandomPassword(8);
        String encodedPassword = passwordEncoder.encode(newPassword);
        String message = "Mật khâu mới của bạn là  : " +newPassword;

        boolean result =  emailService.sendMail(
                email,
                "Mật khẩu mới",
                message
        );
        if(result == false){
            throw new AppException(ErrorCode.SEND_EMAIL_FAIL);
        }
        account.get().setPassword(encodedPassword);
        accountRepository.save(account.get());
        return "Cập nhật mật khẩu mới thành công";
    }

    @Override
    public Boolean checkEmailCodeInvalid(ForgetPasswordCodeDTO forgetPasswordCodeDTO) throws ParseException {
        Optional<Account> account = accountRepository.findByEmail(forgetPasswordCodeDTO.getEmail());
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);
//            return false;
        }
        Optional<ForgetPasswordCode> forgetPasswordCode = forgetPasswordCodeRepository.findRecordByCodeAndAccount(
                account.get() , forgetPasswordCodeDTO.getCode());
        if (forgetPasswordCode.isEmpty()) {
//            throw new AppException(ErrorCode.CODE_INVALID);
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatedDate = now.format(formatter);
        String expiresDate = formatDate.format(forgetPasswordCode.get().getExpiredDate());
        Date expiredDate = formatDate.parse(expiresDate);
        if (formatDate.parse(stringFormatedDate).after(expiredDate)) {
            throw new AppException(ErrorCode.CODE_EXPIRED);
//            return false;
        }
        forgetPasswordCodeRepository.delete(forgetPasswordCode.get());
        return true;
    }

    private String generateRandomPassword(int length) {
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String DIGITS = "0123456789";
        final String SPECIAL_CHARS = "@$!%*?&";
        final SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder(length);
        List<Character> passwordChars = new ArrayList<>();

        // Ensure each type of character is present at least once
        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // Fill the rest of the password length with random characters
        String allChars = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;
        for (int i = 4; i < length; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle to prevent any predictable sequence
        Collections.shuffle(passwordChars);

        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}