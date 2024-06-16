package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_C_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_C_RecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sat-sf-c")
public class SAT_SF_C_RecordController {

    @Autowired
    private SAT_SF_C_RecordService sat_sf_c_recordService;

    @PostMapping
    public ApiResponse<SAT_SF_C_Record> createSAT_SF_C_Record(@RequestBody @Valid SAT_SF_C_RecordDTO sat_sf_c_recordDTO) {

        SAT_SF_C_Record createdSAT_SF_C_Record= sat_sf_c_recordService.createSAT_SF_C_Record(sat_sf_c_recordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<SAT_SF_C_Record>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdSAT_SF_C_Record)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<SAT_SF_C_Record> getSAT_SF_C_RecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SAT_SF_C_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(sat_sf_c_recordService.getSAT_SF_C_RecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<SAT_SF_C_Record>> getAllSAT_SF_C_Records() {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<SAT_SF_C_Record>>builder()
                        .code(HttpStatus.OK.value())
                        .result(sat_sf_c_recordService.getAllSAT_SF_C_Records())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<SAT_SF_C_Record> updateSAT_SF_C_Record( @PathVariable Integer id,@RequestBody @Valid SAT_SF_C_RecordDTO sat_sf_c_recordDTO) {
        SAT_SF_C_Record updatedSAT_SF_C_Record = sat_sf_c_recordService.updateSAT_SF_C_Record(id,sat_sf_c_recordDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SAT_SF_C_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedSAT_SF_C_Record)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<SAT_SF_C_Record> deleteSAT_SF_C_Record(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<SAT_SF_C_Record>builder()
                        .code(HttpStatus.OK.value())
                        .result(  sat_sf_c_recordService.deleteSAT_SF_C_Record(id))
                        .build()).getBody();
    }
}