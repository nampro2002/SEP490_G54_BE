package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;

import java.util.List;
import java.util.Optional;

public interface ForgetPasswordCodeService {
    ForgetPasswordCode createForgetPasswordCode(ForgetPasswordCodeDTO forgetPasswordCodeDTO);
    Optional<ForgetPasswordCode> getForgetPasswordCodeById(Integer id);
    List<ForgetPasswordCode> getAllForgetPasswordCodes();
    ForgetPasswordCode updateForgetPasswordCode(ForgetPasswordCodeDTO forgetPasswordCodeDTO);
    void deleteForgetPasswordCode(Integer id);
}