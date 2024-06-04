package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.NumeralRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.NumeralRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/numeral-records")
public class NumeralRecordController {

    @Autowired
    private NumeralRecordService numeralRecordService;

    @PostMapping
    public ResponseEntity<NumeralRecord> createNumeralRecord(@RequestBody NumeralRecordDTO numeralRecordDTO) {

        NumeralRecord createdNumeralRecord= numeralRecordService.createNumeralRecord(numeralRecordDTO);
        return ResponseEntity.ok(createdNumeralRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NumeralRecord> getNumeralRecordById(@PathVariable Integer id) {
        Optional<NumeralRecord> numeralRecord = numeralRecordService.getNumeralRecordById(id);
        return numeralRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NumeralRecord>> getAllNumeralRecords() {
        List<NumeralRecord> numeralRecords = numeralRecordService.getAllNumeralRecords();
        return ResponseEntity.ok(numeralRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NumeralRecord> updateNumeralRecord(@PathVariable Integer id, @RequestBody NumeralRecordDTO numeralRecordDTO) {
        numeralRecordDTO.setId(id);
        NumeralRecord updatedNumeralRecord = numeralRecordService.updateNumeralRecord(numeralRecordDTO);
        return ResponseEntity.ok(updatedNumeralRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNumeralRecord(@PathVariable Integer id) {
        numeralRecordService.deleteNumeralRecord(id);
        return ResponseEntity.noContent().build();
    }
}