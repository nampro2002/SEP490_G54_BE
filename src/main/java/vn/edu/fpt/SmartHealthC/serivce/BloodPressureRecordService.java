package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;

import java.util.List;
import java.util.Optional;

public interface BloodPressureRecordService {
    BloodPressureRecord createBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO);
    Optional<BloodPressureRecord> getBloodPressureRecordById(Integer id);
    List<BloodPressureRecord> getAllBloodPressureRecords();
    BloodPressureRecord updateBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO);
    void deleteBloodPressureRecord(Integer id);
}