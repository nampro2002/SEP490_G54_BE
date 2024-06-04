package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.BloodPressureRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class BloodPressureRecordServiceImpl implements BloodPressureRecordService {

    @Autowired
    private BloodPressureRecordRepository bloodPressureRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public BloodPressureRecord createBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO)
    {
        BloodPressureRecord bloodPressureRecord =  BloodPressureRecord.builder()
                .diastole(bloodPressureRecordDTO.getDiastole())
                .systole(bloodPressureRecordDTO.getSystole())
                .weekStart(bloodPressureRecordDTO.getWeekStart())
                .date(bloodPressureRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(bloodPressureRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        bloodPressureRecord.setAppUserId(appUser);
        return  bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public Optional<BloodPressureRecord> getBloodPressureRecordById(Integer id) {
        return bloodPressureRecordRepository.findById(id);
    }

    @Override
    public List<BloodPressureRecord> getAllBloodPressureRecords() {
        return bloodPressureRecordRepository.findAll();
    }

    @Override
    public BloodPressureRecord updateBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO) {
        BloodPressureRecord bloodPressureRecord =  BloodPressureRecord.builder()
                .Id(bloodPressureRecordDTO.getId())
                .diastole(bloodPressureRecordDTO.getDiastole())
                .systole(bloodPressureRecordDTO.getSystole())
                .weekStart(bloodPressureRecordDTO.getWeekStart())
                .date(bloodPressureRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(bloodPressureRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        bloodPressureRecord.setAppUserId(appUser);
        return  bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public void deleteBloodPressureRecord(Integer id) {
        bloodPressureRecordRepository.deleteById(id);
    }


}