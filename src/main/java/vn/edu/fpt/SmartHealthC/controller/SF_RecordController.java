package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SF_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SF_Record;
import vn.edu.fpt.SmartHealthC.serivce.SF_RecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sf")
public class SF_RecordController {

    @Autowired
    private SF_RecordService sf_recordService;

    @PostMapping
    public ApiResponse<SF_Record> createSF_Record(@RequestBody SF_RecordDTO sf_recordDTO) {
        SF_Record createdSF_Record= sf_recordService.createSF_Record(sf_recordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<SF_Record>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdSF_Record)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<SF_Record> getSF_RecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SF_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(sf_recordService.getSF_RecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<SF_Record>> getAllSF_Records() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<SF_Record>>builder()
                        .code(HttpStatus.OK.value())
                        .result(sf_recordService.getAllSF_Records())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<SF_Record> updateSF_Record(@PathVariable Integer id, @RequestBody SF_RecordDTO sf_recordDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SF_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(sf_recordService.updateSF_Record(id, sf_recordDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<SF_Record> deleteSF_Record(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SF_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(sf_recordService.deleteSF_Record(id))
                        .build()).getBody();
    }
}