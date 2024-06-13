package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.serivce.EmailService;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Service
public class ForgetPasswordCodeServiceImpl implements ForgetPasswordCodeService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailService emailService;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomCode(int codeLength) {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    @Override
    public String sendEmailCode(ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
        Optional<Account> account = accountRepository.findByEmail(forgetPasswordCodeDTO.getEmail());
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);
        }
        String codeVerify = generateRandomCode(6);
        String message = "Code xác thực của bạn là : " +codeVerify;

       boolean result =  emailService.sendMail(
                forgetPasswordCodeDTO.getEmail(),
                "Mã xác thực ",
                message
        );
       if(result == false){
           throw new AppException(ErrorCode.SEND_EMAIL_FAIL);
       }
        ForgetPasswordCodeDTO forgetPasswordCodeDTOCreate = ForgetPasswordCodeDTO.builder()
                .code(codeVerify).build();
        ForgetPasswordCode forgetPasswordCode =ForgetPasswordCode.builder()
                .code(codeVerify)
                .accountId(account.get())
                .expiredDate(forgetPasswordCodeDTO.getExpiredDate()).build();
        return codeVerify;
    }
}