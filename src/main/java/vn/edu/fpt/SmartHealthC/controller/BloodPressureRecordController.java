package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blood-pressure")
public class BloodPressureRecordController {

    @Autowired
    private BloodPressureRecordService bloodPressureRecordService;

    @PostMapping
    public ApiResponse<BloodPressureRecord> createBloodPressureRecord(@RequestBody BloodPressureRecordDTO bloodPressureRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(bloodPressureRecordService.createBloodPressureRecord(bloodPressureRecordDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<BloodPressureRecord> getBloodPressureRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.getBloodPressureRecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<BloodPressureRecord>> getAllBloodPressureRecords() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BloodPressureRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.getAllBloodPressureRecords())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<BloodPressureRecord> updateBloodPressureRecord(@RequestBody BloodPressureRecordDTO bloodPressureRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.updateBloodPressureRecord(bloodPressureRecordDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<BloodPressureRecord> deleteBloodPressureRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.deleteBloodPressureRecord(id))
                        .build()).getBody();
    }
}