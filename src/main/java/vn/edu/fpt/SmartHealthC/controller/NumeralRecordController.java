package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.NumeralRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.NumeralRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/numeral-records")
public class NumeralRecordController {

    @Autowired
    private NumeralRecordService numeralRecordService;

    @PostMapping
    public ApiResponse<NumeralRecord> createNumeralRecord(@RequestBody NumeralRecordDTO numeralRecordDTO) {

        NumeralRecord createdNumeralRecord= numeralRecordService.createNumeralRecord(numeralRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<NumeralRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdNumeralRecord)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<NumeralRecord> getNumeralRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<NumeralRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(numeralRecordService.getNumeralRecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<NumeralRecord>> getAllNumeralRecords() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<NumeralRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(numeralRecordService.getAllNumeralRecords())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<NumeralRecord> updateNumeralRecord(@PathVariable Integer id,@RequestBody NumeralRecordDTO numeralRecordDTO) {
        NumeralRecord updatedNumeralRecord = numeralRecordService.updateNumeralRecord(id,numeralRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<NumeralRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedNumeralRecord)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<NumeralRecord> deleteNumeralRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<NumeralRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(numeralRecordService.deleteNumeralRecord(id))
                        .build()).getBody();
    }
}