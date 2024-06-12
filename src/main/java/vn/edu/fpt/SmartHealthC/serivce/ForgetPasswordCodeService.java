package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;

public interface ForgetPasswordCodeService {
    String sendEmailCode(ForgetPasswordCodeDTO forgetPasswordCodeDTO);
}