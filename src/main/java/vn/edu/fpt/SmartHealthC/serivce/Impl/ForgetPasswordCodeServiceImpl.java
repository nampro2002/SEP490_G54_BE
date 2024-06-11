package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.ForgetPasswordCodeRepository;
import vn.edu.fpt.SmartHealthC.repository.StepRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class ForgetPasswordCodeServiceImpl implements ForgetPasswordCodeService {

    @Autowired
    private ForgetPasswordCodeRepository forgetPasswordCodeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ForgetPasswordCode createForgetPasswordCode(ForgetPasswordCodeDTO forgetPasswordCodeDTO)
    {
        ForgetPasswordCode forgetPasswordCode =  ForgetPasswordCode.builder()
                .code(forgetPasswordCodeDTO.getCode())
                .isUsed(forgetPasswordCodeDTO.getIsUsed()).build();
        Account account = accountRepository.findById(forgetPasswordCodeDTO.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        forgetPasswordCode.setAccountId(account);
        return  forgetPasswordCodeRepository.save(forgetPasswordCode);
    }

    @Override
    public ForgetPasswordCode getForgetPasswordCodeById(Integer id) {
        Optional<ForgetPasswordCode> forgetPasswordCode = forgetPasswordCodeRepository.findById(id);
        if (forgetPasswordCode.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return forgetPasswordCode.get();
    }

    @Override
    public List<ForgetPasswordCode> getAllForgetPasswordCodes() {
        return forgetPasswordCodeRepository.findAll();
    }

    @Override
    public ForgetPasswordCode updateForgetPasswordCode( Integer id,ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
        ForgetPasswordCode forgetPasswordCode = getForgetPasswordCodeById(id);
        forgetPasswordCode.setCode(forgetPasswordCode.getCode());
        forgetPasswordCode.setIsUsed(forgetPasswordCodeDTO.getIsUsed());
        return  forgetPasswordCodeRepository.save(forgetPasswordCode);
    }

    @Override
    public ForgetPasswordCode deleteForgetPasswordCode(Integer id) {
        ForgetPasswordCode forgetPasswordCode = getForgetPasswordCodeById(id);
        forgetPasswordCodeRepository.deleteById(id);
        return forgetPasswordCode;
    }


}