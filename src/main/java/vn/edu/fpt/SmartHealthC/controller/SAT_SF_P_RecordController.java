package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_P_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_P_RecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sat-sf-p")
public class SAT_SF_P_RecordController {

    @Autowired
    private SAT_SF_P_RecordService sat_sf_p_recordService;

    @PostMapping
    public ApiResponse<SAT_SF_P_Record> createSAT_SF_P_Record(@RequestBody SAT_SF_P_RecordDTO sat_sf_p_recordDTO) {

        SAT_SF_P_Record createdSAT_SF_P_Record= sat_sf_p_recordService.createSAT_SF_P_Record(sat_sf_p_recordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<SAT_SF_P_Record>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdSAT_SF_P_Record)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<SAT_SF_P_Record> getSAT_SF_P_RecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SAT_SF_P_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(sat_sf_p_recordService.getSAT_SF_P_RecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<SAT_SF_P_Record>> getAllSAT_SF_P_Records() {
        List<SAT_SF_P_Record> sat_sf_p_records = sat_sf_p_recordService.getAllSAT_SF_P_Records();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<SAT_SF_P_Record>>builder()
                        .code(HttpStatus.OK.value())
                        .result(sat_sf_p_records)
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<SAT_SF_P_Record> updateSAT_SF_P_Record(@PathVariable Integer id, @RequestBody SAT_SF_P_RecordDTO sat_sf_p_recordDTO) {
        SAT_SF_P_Record updatedSAT_SF_P_Record = sat_sf_p_recordService.updateSAT_SF_P_Record(id,sat_sf_p_recordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SAT_SF_P_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedSAT_SF_P_Record)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<SAT_SF_P_Record> deleteSAT_SF_P_Record(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SAT_SF_P_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result( sat_sf_p_recordService.deleteSAT_SF_P_Record(id))
                        .build()).getBody();
    }
}