package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.List;
import java.util.Optional;

public interface ActivityRecordService {
    ActivityRecord createActivityRecord(ActivityRecordDTO activityRecordDTO);
    ActivityRecord getActivityRecordById(Integer id);
    List<ActivityRecord> getAllActivityRecords();
    ActivityRecord updateActivityRecord(Integer id, ActivityRecordDTO activityRecordDTO);
    ActivityRecord deleteActivityRecord(Integer id);
}