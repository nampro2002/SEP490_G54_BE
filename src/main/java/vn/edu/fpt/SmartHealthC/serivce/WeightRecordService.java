package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface WeightRecordService {
    WeightRecord createWeightRecord(WeightRecordDTO weightRecordDTO) throws ParseException;
    WeightRecord getWeightRecordById(Integer id);
    List<WeightRecord> getAllWeightRecords();
    void updateWeightRecord(Integer id, WeightRecordDTO weightRecordDTO);
    WeightRecord deleteWeightRecord(Integer id);
    List<WeightResponseDTO> getWeightRecordList(Integer userId);

    WeightResponseChartDTO getDataChart() throws ParseException;

    Boolean checkPlanPerDay(String weekStart) throws ParseException;
}