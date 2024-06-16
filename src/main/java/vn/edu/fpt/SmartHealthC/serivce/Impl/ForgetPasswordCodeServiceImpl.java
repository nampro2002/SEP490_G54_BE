package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.ForgetPasswordCodeRepository;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

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
        return codeVerify;
    }

    @Override
    public boolean verifyAndChangePassword(ForgetPasswordCodeDTO forgetPasswordCodeDTO) throws ParseException {
        Optional<Account> account = accountRepository.findByEmail(forgetPasswordCodeDTO.getEmail());
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);
        }
        Optional<ForgetPasswordCode> forgetPasswordCode = forgetPasswordCodeRepository.findRecordByCodeAndAccount(
                account.get() , forgetPasswordCodeDTO.getCode());
        if (forgetPasswordCode.isEmpty()) {
            throw new AppException(ErrorCode.CODE_INVALID);
        }
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatedDate = now.format(formatter);

        if (formatDate.parse(stringFormatedDate).after(formatDate.parse(forgetPasswordCode.get().getExpiredDate().toString()))) {
            throw new AppException(ErrorCode.CODE_EXPIRED);
        }
//        System.out.printf(forgetPasswordCode.get().getExpiredDate().toString());
//        System.out.println(stringFormatedDate);
        account.get().setPassword(passwordEncoder.encode(forgetPasswordCodeDTO.getPassword()));
        accountRepository.save(account.get());
        forgetPasswordCodeRepository.delete(forgetPasswordCode.get());
        return true;
    }
}