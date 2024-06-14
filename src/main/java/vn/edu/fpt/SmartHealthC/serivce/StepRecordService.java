package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.List;

public interface StepRecordService {
    StepRecord createStepRecord(StepRecordDTO stepRecordDTO);
    StepRecord getStepRecordById(Integer id);
    List<StepRecordResListDTO> getAllStepRecords(Integer id);
    StepRecord updateStepRecord( Integer id, StepRecordDTO stepRecordDTO);
    StepRecord deleteStepRecord(Integer id);
}