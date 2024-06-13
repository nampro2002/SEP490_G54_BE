package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/mental-records")
public class MentalRecordController {

    @Autowired
    private MentalRecordService mentalRecordService;

    @PostMapping
    public ApiResponse<MentalRecordResponseDTO> createMentalRecord(@RequestBody MentalRecordDTO mentalRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MentalRecordResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result( mentalRecordService.createMentalRecord(mentalRecordDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MentalRecordResponseDTO> getMentalRecordById(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getMentalRecordById(id))
                        .build()).getBody();
    }

    @GetMapping("getByAppUser/{id}")
    public ApiResponse<List<MentalRecordListResDTO>> getAllMentalRecords(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getAllMentalRecords(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MentalRecordResponseDTO> updateMentalRecord(@PathVariable Integer id, @RequestBody MentalRecordDTO mentalRecordDTO) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.updateMentalRecord(id,mentalRecordDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MentalRecordResponseDTO> deleteMentalRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result( mentalRecordService.deleteMentalRecord(id))
                        .build()).getBody();
    }
}