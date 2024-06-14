package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.List;
import java.util.Optional;

public interface DietRecordService {
    DietRecord createDietRecord(DietRecordDTO dietRecordDTO);
    DietRecord getDietRecordById(Integer id);
    List<DietRecordListResDTO> getAllDietRecords(Integer id);
    DietRecord updateDietRecord(Integer id,DietRecordDTO dietRecordDTO);
    DietRecord deleteDietRecord(Integer id);
}