package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
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
                .weekStart(stepRecordDTO.getWeekStart())
                .date(stepRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(stepRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        stepRecord.setAppUserId(appUser);
        return  stepRecordRepository.save(stepRecord);
    }

    @Override
    public Optional<StepRecord> getStepRecordById(Integer id) {
        return stepRecordRepository.findById(id);
    }

    @Override
    public List<StepRecord> getAllStepRecords() {
        return stepRecordRepository.findAll();
    }

    @Override
    public StepRecord updateStepRecord(StepRecordDTO stepRecordDTO) {
        StepRecord stepRecord =  StepRecord.builder()
                .Id(stepRecordDTO.getId())
                .plannedStepPerDay(stepRecordDTO.getPlannedStepPerDay())
                .weekStart(stepRecordDTO.getWeekStart())
                .date(stepRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(stepRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        stepRecord.setAppUserId(appUser);
        return  stepRecordRepository.save(stepRecord);
    }

    @Override
    public void deleteStepRecord(Integer id) {
        stepRecordRepository.deleteById(id);
    }


}