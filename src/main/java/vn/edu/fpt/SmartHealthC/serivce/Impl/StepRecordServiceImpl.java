package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.StepRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class StepRecordServiceImpl implements StepRecordService {

    @Autowired
    private StepRecordRepository stepRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public StepRecord createStepRecord(StepRecordDTO stepRecordDTO)
    {
        StepRecord stepRecord =  StepRecord.builder()
                .plannedStepPerDay(stepRecordDTO.getPlannedStepPerDay())
                .actualValue(stepRecordDTO.getActualValue())
                .weekStart(stepRecordDTO.getWeekStart())
                .date(stepRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(stepRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        stepRecord.setAppUserId(appUser);
        return  stepRecordRepository.save(stepRecord);
    }

    @Override
    public StepRecord getStepRecordById(Integer id) {
        Optional<StepRecord> stepRecord = stepRecordRepository.findById(id);
        if(stepRecord.isEmpty()){
            throw new AppException(ErrorCode.STEP_RECORD_NOT_FOUND);
        }
        return stepRecord.get();
    }

    @Override
    public List<StepRecord> getAllStepRecords() {
        return stepRecordRepository.findAll();
    }

    @Override
    public StepRecord updateStepRecord( Integer id, StepRecordDTO stepRecordDTO) {
        StepRecord stepRecord =  getStepRecordById(id);
        stepRecord.setActualValue(stepRecordDTO.getActualValue());
        stepRecord.setPlannedStepPerDay(stepRecordDTO.getPlannedStepPerDay());
        stepRecord.setWeekStart(stepRecordDTO.getWeekStart());
        stepRecord.setDate(stepRecordDTO.getDate());
        return  stepRecordRepository.save(stepRecord);
    }

    @Override
    public StepRecord deleteStepRecord(Integer id) {
        StepRecord stepRecord =  getStepRecordById(id);
        stepRecordRepository.deleteById(id);
        return stepRecord;
    }


}