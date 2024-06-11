package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.SF_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/step-records")
public class StepRecordController {

    @Autowired
    private StepRecordService stepRecordService;

    @PostMapping
    public ApiResponse<StepRecord> createStepRecord(@RequestBody StepRecordDTO stepRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(stepRecordService.createStepRecord(stepRecordDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<StepRecord> getStepRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.getStepRecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<StepRecord>> getAllStepRecords() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<StepRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.getAllStepRecords())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<StepRecord> updateStepRecord(@PathVariable Integer id, @RequestBody StepRecordDTO stepRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.updateStepRecord(id, stepRecordDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<StepRecord> deleteStepRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result( stepRecordService.deleteStepRecord(id))
                        .build()).getBody();
    }
}