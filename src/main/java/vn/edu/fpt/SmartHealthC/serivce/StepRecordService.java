package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.List;
import java.util.Optional;

public interface StepRecordService {
    StepRecord createStepRecord(StepRecordDTO stepRecordDTO);
    StepRecord getStepRecordById(Integer id);
    List<StepRecord> getAllStepRecords();
    StepRecord updateStepRecord( Integer id, StepRecordDTO stepRecordDTO);
    StepRecord deleteStepRecord(Integer id);
}