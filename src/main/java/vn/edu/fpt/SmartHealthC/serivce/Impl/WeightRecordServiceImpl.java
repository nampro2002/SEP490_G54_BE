package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
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
                .orElseThrow(() -> new AppException(ErrorCode.APP_USER_NOT_FOUND));
        weightRecord.setAppUserId(appUser);
        return  weightRecordRepository.save(weightRecord);
    }

    @Override
    public WeightRecord getWeightRecordById(Integer id) {
        Optional<WeightRecord> weightRecord  = weightRecordRepository.findById(id);
        if(weightRecord.isEmpty()){
            throw new AppException(ErrorCode.WEIGHT_RECORD_NOT_FOUND);
        }
        return weightRecord.get();
    }

    @Override
    public List<WeightRecord> getAllWeightRecords() {
        return weightRecordRepository.findAll();
    }

    @Override
    public WeightRecord updateWeightRecord(Integer id, WeightRecordDTO weightRecordDTO) {

        WeightRecord weightRecord = getWeightRecordById(id);
        weightRecord.setWeight(weightRecordDTO.getWeight());
        return  weightRecordRepository.save(weightRecord);
    }

    @Override
    public WeightRecord deleteWeightRecord(Integer id) {
        WeightRecord weightRecord = getWeightRecordById(id);
        weightRecordRepository.deleteById(id);
        return weightRecord;
    }


}