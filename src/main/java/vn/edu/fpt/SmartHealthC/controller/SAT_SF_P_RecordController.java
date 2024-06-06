package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_P_RecordDTO;
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
    public ResponseEntity<SAT_SF_P_Record> createSAT_SF_P_Record(@RequestBody SAT_SF_P_RecordDTO sat_sf_p_recordDTO) {

        SAT_SF_P_Record createdSAT_SF_P_Record= sat_sf_p_recordService.createSAT_SF_P_Record(sat_sf_p_recordDTO);
        return ResponseEntity.ok(createdSAT_SF_P_Record);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SAT_SF_P_Record> getSAT_SF_P_RecordById(@PathVariable Integer id) {
        Optional<SAT_SF_P_Record> sat_sf_p_record = sat_sf_p_recordService.getSAT_SF_P_RecordById(id);
        return sat_sf_p_record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SAT_SF_P_Record>> getAllSAT_SF_P_Records() {
        List<SAT_SF_P_Record> sat_sf_p_records = sat_sf_p_recordService.getAllSAT_SF_P_Records();
        return ResponseEntity.ok(sat_sf_p_records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SAT_SF_P_Record> updateSAT_SF_P_Record(@PathVariable Integer id, @RequestBody SAT_SF_P_RecordDTO sat_sf_p_recordDTO) {
        sat_sf_p_recordDTO.setId(id);
        SAT_SF_P_Record updatedSAT_SF_P_Record = sat_sf_p_recordService.updateSAT_SF_P_Record(sat_sf_p_recordDTO);
        return ResponseEntity.ok(updatedSAT_SF_P_Record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSAT_SF_P_Record(@PathVariable Integer id) {
        sat_sf_p_recordService.deleteSAT_SF_P_Record(id);
        return ResponseEntity.noContent().build();
    }
}