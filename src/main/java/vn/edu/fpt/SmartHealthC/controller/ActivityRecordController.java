package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.serivce.ActivityRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/activity-records")
public class ActivityRecordController {

    @Autowired
    private ActivityRecordService activityRecordService;

    @PostMapping
    public ApiResponse<Void> createActivityRecord(@RequestBody @Valid ActivityRecordCreateDTO activityRecordDTO) throws ParseException {
        activityRecordService.createActivityRecord(activityRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }


    @GetMapping("/mobile/chart")
    public ApiResponse<ActivityResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ActivityResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.getDataChart())
                        .build()).getBody();
    }

    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<ActivityRecordResListDTO>> getAllActivityRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ActivityRecordResListDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.getAllActivityRecords(id))
                        .build()).getBody();
    }
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }

    @PutMapping("")
    public ApiResponse<ActivityRecord> updateActivityRecord(@RequestBody @Valid ActivityRecordUpdateDTO activityRecordDTO) throws ParseException {
        ActivityRecord updatedActivityRecord = activityRecordService.updateActivityRecord(activityRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ActivityRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedActivityRecord)
                        .build()).getBody();
    }
    //Test only
    @GetMapping("/{id}")
    public ApiResponse<ActivityRecord> getActivityRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ActivityRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.getActivityRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<ActivityRecord> deleteActivityRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ActivityRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(activityRecordService.deleteActivityRecord(id))
                        .build()).getBody();
    }
}