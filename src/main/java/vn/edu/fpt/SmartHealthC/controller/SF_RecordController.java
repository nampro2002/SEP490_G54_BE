package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.SAT_SF_P_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.SF_RecordDTO;
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
    public ResponseEntity<SF_Record> createSF_Record(@RequestBody SF_RecordDTO sf_recordDTO) {

        SF_Record createdSF_Record= sf_recordService.createSF_Record(sf_recordDTO);
        return ResponseEntity.ok(createdSF_Record);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SF_Record> getSF_RecordById(@PathVariable Integer id) {
        Optional<SF_Record> sf_record = sf_recordService.getSF_RecordById(id);
        return sf_record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SF_Record>> getAllSF_Records() {
        List<SF_Record> sf_records = sf_recordService.getAllSF_Records();
        return ResponseEntity.ok(sf_records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SF_Record> updateSF_Record(@PathVariable Integer id, @RequestBody SF_RecordDTO sf_recordDTO) {
        sf_recordDTO.setId(id);
        SF_Record updatedSF_Record = sf_recordService.updateSF_Record(sf_recordDTO);
        return ResponseEntity.ok(updatedSF_Record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSF_Record(@PathVariable Integer id) {
        sf_recordService.deleteSF_Record(id);
        return ResponseEntity.noContent().build();
    }
}