package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/blood-pressure")
public class BloodPressureRecordController {

    @Autowired
    private BloodPressureRecordService bloodPressureRecordService;

    @PostMapping
    public ApiResponse<BloodPressureRecord> createBloodPressureRecord(@RequestBody @Valid BloodPressureRecordDTO bloodPressureRecordDTO) {
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

    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<BloodPressureResponseDTO>> getAllBloodPressureRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BloodPressureResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.getListBloodPressureRecordsByUser(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<BloodPressureRecord> updateBloodPressureRecord(@PathVariable Integer id, @RequestBody @Valid BloodPressureRecordDTO bloodPressureRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.updateBloodPressureRecord(id, bloodPressureRecordDTO))
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