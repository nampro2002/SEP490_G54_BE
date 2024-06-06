package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.WeightRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;
import vn.edu.fpt.SmartHealthC.serivce.WeightRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class WeightRecordServiceImpl implements WeightRecordService {

    @Autowired
    private WeightRecordRepository weightRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public WeightRecord createWeightRecord(WeightRecordDTO weightRecordDTO)
    {
        WeightRecord weightRecord =  WeightRecord.builder()
                .weight(weightRecordDTO.getWeight())
                .date(weightRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(weightRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        weightRecord.setAppUserId(appUser);
        return  weightRecordRepository.save(weightRecord);
    }

    @Override
    public Optional<WeightRecord> getWeightRecordById(Integer id) {
        return weightRecordRepository.findById(id);
    }

    @Override
    public List<WeightRecord> getAllWeightRecords() {
        return weightRecordRepository.findAll();
    }

    @Override
    public WeightRecord updateWeightRecord(WeightRecordDTO weightRecordDTO) {
        WeightRecord weightRecord =  WeightRecord.builder()
                .Id(weightRecordDTO.getId())
                .weight(weightRecordDTO.getWeight())
                .date(weightRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(weightRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        weightRecord.setAppUserId(appUser);
        return  weightRecordRepository.save(weightRecord);
    }

    @Override
    public void deleteWeightRecord(Integer id) {
        weightRecordRepository.deleteById(id);
    }


}