package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class MentalRecordServiceImpl implements MentalRecordService {

    @Autowired
    private MentalRecordRepository mentalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MentalRuleRepository mentalRuleRepository;

    @Override
    public MentalRecord createMentalRecord(MentalRecordDTO mentalRecordDTO) {
        MentalRecord mentalRecord =  MentalRecord.builder()
                .status(mentalRecordDTO.isStatus())
                .weekStart(mentalRecordDTO.getWeekStart())
                .date(mentalRecordDTO.getDate())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(mentalRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        mentalRecord.setAppUserId(appUser.get());
        Optional<MentalRule> mentalRule = mentalRuleRepository.findById(mentalRecordDTO.getMentalRuleId());
        if(mentalRule.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }
        mentalRecord.setMentalRule(mentalRule.get());
        return mentalRecordRepository.save(mentalRecord);
    }

    @Override
    public MentalRecord getMentalRecordById(Integer id) {
        Optional<MentalRecord> mentalRecord = mentalRecordRepository.findById(id);
        if(mentalRecord.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_NOT_FOUND);
        }
        return mentalRecord.get();
    }

    @Override
    public List<MentalRecord> getAllMentalRecords() {
        return mentalRecordRepository.findAll();
    }

    @Override
    public MentalRecord updateMentalRecord(Integer id,MentalRecordDTO mentalRecordDTO) {
        MentalRecord mentalRecord =  getMentalRecordById(id);
        mentalRecord.setStatus(mentalRecordDTO.isStatus());
        mentalRecord.setWeekStart(mentalRecordDTO.getWeekStart());
        mentalRecord.setDate(mentalRecordDTO.getDate());
        return mentalRecordRepository.save(mentalRecord);
    }

    @Override
    public MentalRecord deleteMentalRecord(Integer id) {
        MentalRecord mentalRecord = getMentalRecordById(id);
        mentalRecordRepository.deleteById(id);
        return mentalRecord;
    }
}