package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.serivce.ActivityRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/activity-records")
public class ActivityRecordController {

    @Autowired
    private ActivityRecordService activityRecordService;

    @PostMapping
    public ApiResponse<ActivityRecord> createActivityRecord(@RequestBody @Valid ActivityRecordDTO activityRecordDTO) {

        ActivityRecord createdActivityRecord= activityRecordService.createActivityRecord(activityRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ActivityRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdActivityRecord)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<ActivityRecord> getActivityRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ActivityRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.getActivityRecordById(id))
                        .build()).getBody();
    }

    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<ActivityRecordResListDTO>> getAllActivityRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ActivityRecordResListDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.getAllActivityRecords(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<ActivityRecord> updateActivityRecord(@PathVariable Integer id, @RequestBody @Valid ActivityRecordDTO activityRecordDTO) {
        ActivityRecord updatedActivityRecord = activityRecordService.updateActivityRecord(id, activityRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ActivityRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedActivityRecord)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<ActivityRecord> deleteActivityRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ActivityRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.deleteActivityRecord(id))
                        .build()).getBody();
    }
}