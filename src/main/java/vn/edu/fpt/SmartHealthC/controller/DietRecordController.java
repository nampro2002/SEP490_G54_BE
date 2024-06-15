package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diet-records")
public class DietRecordController {

    @Autowired
    private DietRecordService dietRecordService;

    @PostMapping
    public ApiResponse<DietRecord> createDietRecord(@RequestBody @Valid DietRecordDTO dietRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(dietRecordService.createDietRecord(dietRecordDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<DietRecord> getDietRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getDietRecordById(id))
                        .build()).getBody();
    }

    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<DietRecordListResDTO>> getAllDietRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DietRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getAllDietRecords(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<DietRecord> updateDietRecord(@PathVariable Integer id, @RequestBody @Valid DietRecordDTO dietRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.updateDietRecord(id, dietRecordDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<DietRecord> deleteDietRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.deleteDietRecord(id))
                        .build()).getBody();
    }
}