package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.SAT_SF_C_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
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
    public ResponseEntity<SAT_SF_C_Record> createSAT_SF_C_Record(@RequestBody SAT_SF_C_RecordDTO sat_sf_c_recordDTO) {

        SAT_SF_C_Record createdSAT_SF_C_Record= sat_sf_c_recordService.createSAT_SF_C_Record(sat_sf_c_recordDTO);
        return ResponseEntity.ok(createdSAT_SF_C_Record);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SAT_SF_C_Record> getSAT_SF_C_RecordById(@PathVariable Integer id) {
        Optional<SAT_SF_C_Record> sat_sf_c_record = sat_sf_c_recordService.getSAT_SF_C_RecordById(id);
        return sat_sf_c_record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SAT_SF_C_Record>> getAllSAT_SF_C_Records() {
        List<SAT_SF_C_Record> sat_sf_c_records = sat_sf_c_recordService.getAllSAT_SF_C_Records();
        return ResponseEntity.ok(sat_sf_c_records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SAT_SF_C_Record> updateSAT_SF_C_Record(@PathVariable Integer id, @RequestBody SAT_SF_C_RecordDTO sat_sf_c_recordDTO) {
        sat_sf_c_recordDTO.setId(id);
        SAT_SF_C_Record updatedSAT_SF_C_Record = sat_sf_c_recordService.updateSAT_SF_C_Record(sat_sf_c_recordDTO);
        return ResponseEntity.ok(updatedSAT_SF_C_Record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSAT_SF_C_Record(@PathVariable Integer id) {
        sat_sf_c_recordService.deleteSAT_SF_C_Record(id);
        return ResponseEntity.noContent().build();
    }
}