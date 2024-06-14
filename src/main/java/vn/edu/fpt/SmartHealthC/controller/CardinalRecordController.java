package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.serivce.CardinalRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/cardinal-records")
public class CardinalRecordController {

    @Autowired
    private CardinalRecordService cardinalRecordService;

    @PostMapping
    public ApiResponse<CardinalRecord> createCardinalRecord(@RequestBody CardinalRecordDTO cardinalRecordDTO) {

        CardinalRecord createdCardinalRecord = cardinalRecordService.createCardinalRecord(cardinalRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdCardinalRecord)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<CardinalRecord> getCardinalRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getCardinalRecordById(id))
                        .build()).getBody();
    }

    @GetMapping("getByAppUser/{id}")
    public ApiResponse< List<CardinalRecordResponseDTO>> getAllCardinalRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.< List<CardinalRecordResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getAllCardinalRecords(id))
                        .build()).getBody();
    }

    @GetMapping("/vip")
    public ApiResponse< List<CardinalRecord>> getAllCardinalRecordsVip() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.< List<CardinalRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getAllCardinalRecordsVip())
                        .build()).getBody();
    }


    @PutMapping("/{id}")
    public ApiResponse<CardinalRecord> updateCardinalRecord(@PathVariable Integer id, @RequestBody CardinalRecordDTO cardinalRecordDTO) {
        CardinalRecord updatedCardinalRecord = cardinalRecordService.updateCardinalRecord(id, cardinalRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedCardinalRecord)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<CardinalRecord> deleteCardinalRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.deleteCardinalRecord(id))
                        .build()).getBody();
    }
}