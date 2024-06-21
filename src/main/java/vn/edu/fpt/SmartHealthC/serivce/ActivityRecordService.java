package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;

import java.text.ParseException;
import java.util.List;

public interface ActivityRecordService {
    ActivityRecord createActivityRecord(ActivityRecordDTO activityRecordDTO) throws ParseException;
    ActivityRecord getActivityRecordById(Integer id);
    List<ActivityRecordResListDTO> getAllActivityRecords(Integer userId);
    ActivityRecord updateActivityRecord(Integer id, ActivityRecordDTO activityRecordDTO);
    ActivityRecord deleteActivityRecord(Integer id);
}