package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.List;
import java.util.Optional;

public interface DietRecordService {
    DietRecord createDietRecord(DietRecordDTO dietRecordDTO);
    Optional<DietRecord> getDietRecordById(Integer id);
    List<DietRecord> getAllDietRecords();
    DietRecord updateDietRecord(DietRecordDTO dietRecordDTO);
    void deleteDietRecord(Integer id);
}