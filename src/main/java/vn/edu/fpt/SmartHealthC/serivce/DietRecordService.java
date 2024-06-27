package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;

import java.text.ParseException;
import java.util.List;

public interface DietRecordService {
    void createDietRecord(DietRecordCreateDTO dietRecordDTO) throws ParseException;
    DietRecord getDietRecordById(Integer id);
    List<DietRecordListResDTO> getAllDietRecords(Integer id);
    DietRecord updateDietRecord(DietRecordUpdateDTO dietRecordDTO) throws ParseException;
    DietRecord deleteDietRecord(Integer id);

    DietResponseChartDTO getDataChart() throws ParseException;

    Integer getDishPlan(String weekStart) throws ParseException;
}