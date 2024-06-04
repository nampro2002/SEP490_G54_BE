package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
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
        AppUser appUser = appUserRepository.findById(dietRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        dietRecord.setAppUserId(appUser);
        return  dietRecordRepository.save(dietRecord);
    }

    @Override
    public Optional<DietRecord> getDietRecordById(Integer id) {
        return dietRecordRepository.findById(id);
    }

    @Override
    public List<DietRecord> getAllDietRecords() {
        return dietRecordRepository.findAll();
    }

    @Override
    public DietRecord updateDietRecord(DietRecordDTO dietRecordDTO) {

        DietRecord dietRecord =  DietRecord.builder()
                .Id(dietRecordDTO.getId())
                .dishPerDay(dietRecordDTO.getDishPerDay())
                .weekStart(dietRecordDTO.getWeekStart())
                .date(dietRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(dietRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        dietRecord.setAppUserId(appUser);
        return  dietRecordRepository.save(dietRecord);
    }

    @Override
    public void deleteDietRecord(Integer id) {
        dietRecordRepository.deleteById(id);
    }
}