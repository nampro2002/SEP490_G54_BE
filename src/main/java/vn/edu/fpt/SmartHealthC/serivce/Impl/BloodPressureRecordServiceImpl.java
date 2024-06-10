package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
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
    public BloodPressureRecord createBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO) {
        BloodPressureRecord bloodPressureRecord = BloodPressureRecord.builder()
                .diastole(bloodPressureRecordDTO.getDiastole())
                .systole(bloodPressureRecordDTO.getSystole())
                .weekStart(bloodPressureRecordDTO.getWeekStart())
                .date(bloodPressureRecordDTO.getDate()).build();
        Optional<AppUser> appUser = appUserRepository.findById(bloodPressureRecordDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        bloodPressureRecord.setAppUserId(appUser.get());
        return bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public BloodPressureRecord getBloodPressureRecordById(Integer id) {
        Optional<BloodPressureRecord> bloodPressureRecord = bloodPressureRecordRepository.findById(id);
        if(bloodPressureRecord.isEmpty()) {
            throw new AppException(ErrorCode.BLOOD_PRESSURE_NOT_FOUND);
        }
        return bloodPressureRecord.get();
    }

    @Override
    public List<BloodPressureRecord> getAllBloodPressureRecords() {
        return bloodPressureRecordRepository.findAll();
    }

    @Override
    public BloodPressureRecord updateBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(bloodPressureRecordDTO.getId());
        bloodPressureRecord.setDiastole(bloodPressureRecordDTO.getDiastole());
        bloodPressureRecord.setSystole(bloodPressureRecordDTO.getSystole());
        bloodPressureRecord.setWeekStart(bloodPressureRecordDTO.getWeekStart());
        bloodPressureRecord.setDate(bloodPressureRecordDTO.getDate());
        return bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public BloodPressureRecord deleteBloodPressureRecord(Integer id) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(id);
        bloodPressureRecordRepository.deleteById(id);
        return bloodPressureRecord;
    }


}