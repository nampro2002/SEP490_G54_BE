package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
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
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        forgetPasswordCode.setAccountId(account);
        return  forgetPasswordCodeRepository.save(forgetPasswordCode);
    }

    @Override
    public Optional<ForgetPasswordCode> getForgetPasswordCodeById(Integer id) {
        return forgetPasswordCodeRepository.findById(id);
    }

    @Override
    public List<ForgetPasswordCode> getAllForgetPasswordCodes() {
        return forgetPasswordCodeRepository.findAll();
    }

    @Override
    public ForgetPasswordCode updateForgetPasswordCode(ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
         ForgetPasswordCode forgetPasswordCode =  ForgetPasswordCode.builder()
                 .Id(forgetPasswordCodeDTO.getId())
                .code(forgetPasswordCodeDTO.getCode())
                .isUsed(forgetPasswordCodeDTO.getIsUsed()).build();
        Account account = accountRepository.findById(forgetPasswordCodeDTO.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        forgetPasswordCode.setAccountId(account);
        return  forgetPasswordCodeRepository.save(forgetPasswordCode);
    }

    @Override
    public void deleteForgetPasswordCode(Integer id) {
        forgetPasswordCodeRepository.deleteById(id);
    }


}