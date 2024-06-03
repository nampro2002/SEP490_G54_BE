package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/step-records")
public class StepRecordController {

    @Autowired
    private StepRecordService stepRecordService;

    @PostMapping
    public ResponseEntity<StepRecord> createStepRecord(@RequestBody StepRecordDTO stepRecordDTO) {

        StepRecord createdStepRecord= stepRecordService.createStepRecord(stepRecordDTO);
        return ResponseEntity.ok(createdStepRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepRecord> getStepRecordById(@PathVariable Integer id) {
        Optional<StepRecord> stepRecord = stepRecordService.getStepRecordById(id);
        return stepRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<StepRecord>> getAllStepRecords() {
        List<StepRecord> stepRecords = stepRecordService.getAllStepRecords();
        return ResponseEntity.ok(stepRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StepRecord> updateStepRecord(@PathVariable Integer id, @RequestBody StepRecordDTO stepRecordDTO) {
        stepRecordDTO.setId(id);
        StepRecord updatedStepRecord = stepRecordService.updateStepRecord(stepRecordDTO);
        return ResponseEntity.ok(updatedStepRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStepRecord(@PathVariable Integer id) {
        stepRecordService.deleteStepRecord(id);
        return ResponseEntity.noContent().build();
    }
}