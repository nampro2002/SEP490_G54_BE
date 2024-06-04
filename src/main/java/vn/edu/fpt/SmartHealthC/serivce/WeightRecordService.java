package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;

import java.util.List;
import java.util.Optional;

public interface WeightRecordService {
    WeightRecord createWeightRecord(WeightRecordDTO weightRecordDTO);
    Optional<WeightRecord> getWeightRecordById(Integer id);
    List<WeightRecord> getAllWeightRecords();
    WeightRecord updateWeightRecord(WeightRecordDTO weightRecordDTO);
    void deleteWeightRecord(Integer id);
}