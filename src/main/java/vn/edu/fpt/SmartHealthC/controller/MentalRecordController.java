package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<MentalRecord> createMentalRecord(@RequestBody MentalRecord mentalRecord) {
        MentalRecord createdMentalRecord = mentalRecordService.createMentalRecord(mentalRecord);
        return ResponseEntity.ok(createdMentalRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentalRecord> getMentalRecordById(@PathVariable Integer id) {
        Optional<MentalRecord> mentalRecord = mentalRecordService.getMentalRecordById(id);
        return mentalRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MentalRecord>> getAllMentalRecords() {
        List<MentalRecord> mentalRecords = mentalRecordService.getAllMentalRecords();
        return ResponseEntity.ok(mentalRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentalRecord> updateMentalRecord(@PathVariable Integer id, @RequestBody MentalRecord mentalRecord) {
        MentalRecord updatedMentalRecord = mentalRecordService.updateMentalRecord(mentalRecord);
        return ResponseEntity.ok(updatedMentalRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentalRecord(@PathVariable Integer id) {
        mentalRecordService.deleteMentalRecord(id);
        return ResponseEntity.noContent().build();
    }
}