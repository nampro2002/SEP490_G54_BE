package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.serivce.WeightRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weight-records")
public class WeightRecordController {

    @Autowired
    private WeightRecordService weightRecordService;

    @PostMapping
    public ResponseEntity<WeightRecord> createWeightRecord(@RequestBody WeightRecordDTO weightRecordDTO) {

        WeightRecord createdWeightRecord= weightRecordService.createWeightRecord(weightRecordDTO);
        return ResponseEntity.ok(createdWeightRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeightRecord> getWeightRecordById(@PathVariable Integer id) {
        Optional<WeightRecord> weightRecord = weightRecordService.getWeightRecordById(id);
        return weightRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<WeightRecord>> getAllWeightRecords() {
        List<WeightRecord> weightRecords = weightRecordService.getAllWeightRecords();
        return ResponseEntity.ok(weightRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeightRecord> updateWeightRecord(@PathVariable Integer id, @RequestBody WeightRecordDTO weightRecordDTO) {
        weightRecordDTO.setId(id);
        WeightRecord updatedWeightRecord = weightRecordService.updateWeightRecord(weightRecordDTO);
        return ResponseEntity.ok(updatedWeightRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeightRecord(@PathVariable Integer id) {
        weightRecordService.deleteWeightRecord(id);
        return ResponseEntity.noContent().build();
    }
}