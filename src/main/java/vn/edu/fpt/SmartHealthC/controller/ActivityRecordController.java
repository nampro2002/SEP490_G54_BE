package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.ActivityRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.serivce.ActivityRecordService;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity-records")
public class ActivityRecordController {

    @Autowired
    private ActivityRecordService activityRecordService;

    @PostMapping
    public ResponseEntity<ActivityRecord> createActivityRecord(@RequestBody ActivityRecordDTO activityRecordDTO) {

        ActivityRecord createdActivityRecord= activityRecordService.createActivityRecord(activityRecordDTO);
        return ResponseEntity.ok(createdActivityRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityRecord> getActivityRecordById(@PathVariable Integer id) {
        Optional<ActivityRecord> activityRecord = activityRecordService.getActivityRecordById(id);
        return activityRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ActivityRecord>> getAllActivityRecords() {
        List<ActivityRecord> activityRecords = activityRecordService.getAllActivityRecords();
        return ResponseEntity.ok(activityRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityRecord> updateActivityRecord(@PathVariable Integer id, @RequestBody ActivityRecordDTO activityRecordDTO) {
        activityRecordDTO.setId(id);
        ActivityRecord updatedActivityRecord = activityRecordService.updateActivityRecord(activityRecordDTO);
        return ResponseEntity.ok(updatedActivityRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityRecord(@PathVariable Integer id) {
        activityRecordService.deleteActivityRecord(id);
        return ResponseEntity.noContent().build();
    }
}