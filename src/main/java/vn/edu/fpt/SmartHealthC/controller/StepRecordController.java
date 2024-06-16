package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/step-records")
public class StepRecordController {

    @Autowired
    private StepRecordService stepRecordService;

    @PostMapping
    public ApiResponse<StepRecord> createStepRecord(@RequestBody @Valid StepRecordDTO stepRecordDTO) {
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

    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<StepRecordResListDTO>> getAllStepRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<StepRecordResListDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.getAllStepRecords(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<StepRecord> updateStepRecord(@PathVariable Integer id, @RequestBody @Valid StepRecordDTO stepRecordDTO) {
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