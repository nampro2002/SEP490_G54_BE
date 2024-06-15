package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.serivce.WeightRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weight-records")
public class WeightRecordController {

    @Autowired
    private WeightRecordService weightRecordService;

    @PostMapping
    public ApiResponse<WeightRecord> createWeightRecord(@RequestBody WeightRecordDTO weightRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<WeightRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(weightRecordService.createWeightRecord(weightRecordDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<WeightRecord> getWeightRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeightRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.getWeightRecordById(id))
                        .build()).getBody();
    }

    @GetMapping("getByAppUser/{id}")
    public ApiResponse<List<WeightResponseDTO>> getAllWeightRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<WeightResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.getWeightRecordList(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<WeightRecord> updateWeightRecord(@PathVariable Integer id, @RequestBody WeightRecordDTO weightRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeightRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result( weightRecordService.updateWeightRecord(id, weightRecordDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<WeightRecord> deleteWeightRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeightRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.deleteWeightRecord(id))
                        .build()).getBody();
    }
}