package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;

import java.util.List;
import java.util.Optional;

public interface WeightRecordService {
    WeightRecord createWeightRecord(WeightRecordDTO weightRecordDTO);
    WeightRecord getWeightRecordById(Integer id);
    List<WeightRecord> getAllWeightRecords();
    WeightRecord updateWeightRecord(Integer id, WeightRecordDTO weightRecordDTO);
    WeightRecord deleteWeightRecord(Integer id);
    List<WeightResponseDTO> getWeightRecordList(Integer userId);
}