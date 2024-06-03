package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blood-pressure")
public class BloodPressureRecordController {

    @Autowired
    private BloodPressureRecordService bloodPressureRecordService;

    @PostMapping
    public ResponseEntity<BloodPressureRecord> createBloodPressureRecord(@RequestBody BloodPressureRecordDTO bloodPressureRecordDTO) {

        BloodPressureRecord createdBloodPressureRecord= bloodPressureRecordService.createBloodPressureRecord(bloodPressureRecordDTO);
        return ResponseEntity.ok(createdBloodPressureRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodPressureRecord> getBloodPressureRecordById(@PathVariable Integer id) {
        Optional<BloodPressureRecord> bloodPressureRecord = bloodPressureRecordService.getBloodPressureRecordById(id);
        return bloodPressureRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BloodPressureRecord>> getAllBloodPressureRecords() {
        List<BloodPressureRecord> bloodPressureRecords = bloodPressureRecordService.getAllBloodPressureRecords();
        return ResponseEntity.ok(bloodPressureRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodPressureRecord> updateBloodPressureRecord(@PathVariable Integer id, @RequestBody BloodPressureRecordDTO bloodPressureRecordDTO) {
        bloodPressureRecordDTO.setId(id);
        BloodPressureRecord updatedStepRecord = bloodPressureRecordService.updateBloodPressureRecord(bloodPressureRecordDTO);
        return ResponseEntity.ok(updatedStepRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodPressureRecord(@PathVariable Integer id) {
        bloodPressureRecordService.deleteBloodPressureRecord(id);
        return ResponseEntity.noContent().build();
    }
}