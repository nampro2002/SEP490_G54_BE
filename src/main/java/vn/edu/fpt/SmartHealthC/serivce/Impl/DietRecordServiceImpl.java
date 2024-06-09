package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.DietRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.StepRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class DietRecordServiceImpl implements DietRecordService {

    @Autowired
    private DietRecordRepository dietRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public DietRecord createDietRecord(DietRecordDTO dietRecordDTO) {
        DietRecord dietRecord =  DietRecord.builder()
                .dishPerDay(dietRecordDTO.getDishPerDay())
                .weekStart(dietRecordDTO.getWeekStart())
                .date(dietRecordDTO.getDate()).build();
        Optional<AppUser> appUser = appUserRepository.findById(dietRecordDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        dietRecord.setAppUserId(appUser.get());
        return  dietRecordRepository.save(dietRecord);
    }

    @Override
    public DietRecord getDietRecordById(Integer id) {
        Optional<DietRecord> dietRecord = dietRecordRepository.findById(id);
        if (dietRecord.isEmpty()) {
            throw new AppException(ErrorCode.DIET_RECORD_NOT_FOUND);
        }
        return dietRecord.get();
    }

    @Override
    public List<DietRecord> getAllDietRecords() {
        return dietRecordRepository.findAll();
    }

    @Override
    public DietRecord updateDietRecord(DietRecordDTO dietRecordDTO) {
        DietRecord dietRecord = getDietRecordById(dietRecordDTO.getId());
        dietRecord.setDishPerDay(dietRecordDTO.getDishPerDay());
        dietRecord.setWeekStart(dietRecordDTO.getWeekStart());
        dietRecord.setDate(dietRecordDTO.getDate());
        return  dietRecordRepository.save(dietRecord);
    }

    @Override
    public DietRecord deleteDietRecord(Integer id) {
        DietRecord dietRecord = getDietRecordById(id);
        dietRecordRepository.deleteById(id);
        return dietRecord;
    }
}