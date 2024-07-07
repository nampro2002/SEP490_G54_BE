package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateContinuousDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface StepRecordService {
    void createStepRecord(StepRecordCreateDTO stepRecordDTO) throws ParseException;
    StepRecord getStepRecordById(Integer id);
    List<StepRecordResListDTO> getAllStepRecords(Integer id);
    void updateStepRecord(StepRecordUpdateDTO stepRecordDTO) throws ParseException;
    StepRecord deleteStepRecord(Integer id);

    StepResponseChartDTO getDataChart() throws ParseException;

    Boolean checkPlanPerDay(String weekStart) throws ParseException;

    void updateStepRecordNEW(StepRecordUpdateContinuousDTO stepRecordDTO) throws ParseException;
//    Date getFirstDayOfWeek(StepRecordUpdateContinuousDTO stepRecordDTO);

}