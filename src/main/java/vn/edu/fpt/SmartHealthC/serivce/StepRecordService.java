package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.text.ParseException;
import java.util.List;

public interface StepRecordService {
    StepRecord createStepRecord(StepRecordCreateDTO stepRecordDTO) throws ParseException;
    StepRecord getStepRecordById(Integer id);
    List<StepRecordResListDTO> getAllStepRecords(Integer id);
    StepRecord updateStepRecord(StepRecordUpdateDTO stepRecordDTO) throws ParseException;
    StepRecord deleteStepRecord(Integer id);
}