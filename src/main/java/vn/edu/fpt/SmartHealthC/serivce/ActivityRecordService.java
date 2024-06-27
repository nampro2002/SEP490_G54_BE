package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;

import java.text.ParseException;
import java.util.List;

public interface ActivityRecordService {
    void createActivityRecord(ActivityRecordCreateDTO activityRecordDTO) throws ParseException;
    ActivityRecord getActivityRecordById(Integer id);
    List<ActivityRecordResListDTO> getAllActivityRecords(Integer userId);
    ActivityRecord updateActivityRecord(ActivityRecordUpdateDTO activityRecordDTO) throws ParseException;
    ActivityRecord deleteActivityRecord(Integer id);

    ActivityResponseChartDTO getDataChart() throws ParseException;
}