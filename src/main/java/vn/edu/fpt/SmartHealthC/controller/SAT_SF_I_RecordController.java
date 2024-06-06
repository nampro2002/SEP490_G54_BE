package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_I_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_I_RecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sat-sf-i")
public class SAT_SF_I_RecordController {

    @Autowired
    private SAT_SF_I_RecordService sat_sf_i_recordService;

    @PostMapping
    public ResponseEntity<SAT_SF_I_Record> createSAT_SF_I_Record(@RequestBody SAT_SF_I_RecordDTO sat_sf_i_recordDTO) {

        SAT_SF_I_Record createdSAT_SF_I_Record= sat_sf_i_recordService.createSAT_SF_I_Record(sat_sf_i_recordDTO);
        return ResponseEntity.ok(createdSAT_SF_I_Record);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SAT_SF_I_Record> getSAT_SF_I_RecordById(@PathVariable Integer id) {
        Optional<SAT_SF_I_Record> sat_sf_i_record = sat_sf_i_recordService.getSAT_SF_I_RecordById(id);
        return sat_sf_i_record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SAT_SF_I_Record>> getAllSAT_SF_I_Records() {
        List<SAT_SF_I_Record> sat_sf_i_records = sat_sf_i_recordService.getAllSAT_SF_I_Records();
        return ResponseEntity.ok(sat_sf_i_records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SAT_SF_I_Record> updateSAT_SF_I_Record(@PathVariable Integer id, @RequestBody SAT_SF_I_RecordDTO sat_sf_i_recordDTO) {
        sat_sf_i_recordDTO.setId(id);
        SAT_SF_I_Record updatedSAT_SF_I_Record = sat_sf_i_recordService.updateSAT_SF_I_Record(sat_sf_i_recordDTO);
        return ResponseEntity.ok(updatedSAT_SF_I_Record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSAT_SF_I_Record(@PathVariable Integer id) {
        sat_sf_i_recordService.deleteSAT_SF_I_Record(id);
        return ResponseEntity.noContent().build();
    }
}