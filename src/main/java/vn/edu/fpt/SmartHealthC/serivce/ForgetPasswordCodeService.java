package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.ChangePasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;

import java.text.ParseException;

public interface ForgetPasswordCodeService {
    String sendEmailCode(String email) throws ParseException;

    boolean verifyAndChangePassword(ChangePasswordCodeDTO changePasswordCodeDTO) throws ParseException;

    String sendPasswordEmail(String email);

    Boolean checkEmailCodeInvalid(ForgetPasswordCodeDTO forgetPasswordCodeDTO) throws ParseException;
}