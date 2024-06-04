package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diet-records")
public class DietRecordController {

    @Autowired
    private DietRecordService dietRecordService;

    @PostMapping
    public ResponseEntity<DietRecord> createDietRecord(@RequestBody DietRecordDTO dietRecordDTO) {

        DietRecord createdDietRecord= dietRecordService.createDietRecord(dietRecordDTO);
        return ResponseEntity.ok(createdDietRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietRecord> getDietRecordById(@PathVariable Integer id) {
        Optional<DietRecord> dietRecord = dietRecordService.getDietRecordById(id);
        return dietRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DietRecord>> getAllDietRecords() {
        List<DietRecord> dietRecords = dietRecordService.getAllDietRecords();
        return ResponseEntity.ok(dietRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietRecord> updateDietRecord(@PathVariable Integer id, @RequestBody DietRecordDTO dietRecordDTO) {
        dietRecordDTO.setId(id);
        DietRecord updatedDietRecord = dietRecordService.updateDietRecord(dietRecordDTO);
        return ResponseEntity.ok(updatedDietRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietRecord(@PathVariable Integer id) {
        dietRecordService.deleteDietRecord(id);
        return ResponseEntity.noContent().build();
    }
}