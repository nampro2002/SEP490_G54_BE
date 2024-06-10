package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mental-records")
public class MentalRecordController {

    @Autowired
    private MentalRecordService mentalRecordService;

    @PostMapping
    public ApiResponse<MentalRecord> createMentalRecord(@RequestBody MentalRecordDTO mentalRecordDTO) {
        MentalRecord createdMentalRecord = mentalRecordService.createMentalRecord(mentalRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MentalRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdMentalRecord)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MentalRecord> getMentalRecordById(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getMentalRecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<MentalRecord>> getAllMentalRecords() {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getAllMentalRecords())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MentalRecord> updateMentalRecord(@PathVariable Integer id, @RequestBody MentalRecordDTO mentalRecordDTO) {
        MentalRecord updatedMentalRecord = mentalRecordService.updateMentalRecord(id,mentalRecordDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedMentalRecord)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MentalRecord> deleteMentalRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result( mentalRecordService.deleteMentalRecord(id))
                        .build()).getBody();
    }
}