package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.NMRDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.serivce.NumeralRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/numeral-records")
public class NumeralRecordController {

    @Autowired
    private NumeralRecordService numeralRecordService;

    @PostMapping
    public ApiResponse<CardinalRecord> createNumeralRecord(@RequestBody NumeralRecordDTO numeralRecordDTO) {

        CardinalRecord createdCardinalRecord = numeralRecordService.createNumeralRecord(numeralRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdCardinalRecord)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<CardinalRecord> getNumeralRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(numeralRecordService.getNumeralRecordById(id))
                        .build()).getBody();
    }

    @GetMapping("getByAppUser/{id}")
    public ApiResponse< List<CardinalRecordResponseDTO>> getAllNumeralRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.< List<CardinalRecordResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(numeralRecordService.getAllNumeralRecords(id))
                        .build()).getBody();
    }

    @GetMapping("/vip")
    public ApiResponse< List<CardinalRecord>> getAllNumeralRecordsVip() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.< List<CardinalRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(numeralRecordService.getAllNumeralRecordsVip())
                        .build()).getBody();
    }


    @PutMapping("/{id}")
    public ApiResponse<CardinalRecord> updateNumeralRecord(@PathVariable Integer id, @RequestBody NumeralRecordDTO numeralRecordDTO) {
        CardinalRecord updatedCardinalRecord = numeralRecordService.updateNumeralRecord(id,numeralRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedCardinalRecord)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<CardinalRecord> deleteNumeralRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(numeralRecordService.deleteNumeralRecord(id))
                        .build()).getBody();
    }
}