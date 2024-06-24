package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/step-records")
public class StepRecordController {

    @Autowired
    private StepRecordService stepRecordService;

    @PostMapping
    public ApiResponse<StepRecord> createStepRecord(@RequestBody @Valid StepRecordCreateDTO stepRecordDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.OK.value())
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

    @PutMapping("")
    public ApiResponse<StepRecord> updateStepRecord( @RequestBody @Valid StepRecordUpdateDTO stepRecordDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.updateStepRecord(stepRecordDTO))
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