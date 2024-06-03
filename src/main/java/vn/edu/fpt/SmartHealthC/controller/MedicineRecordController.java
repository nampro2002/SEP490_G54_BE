package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicine-records")
public class MedicineRecordController {

    @Autowired
    private MedicineRecordService medicineRecordService;

    @PostMapping
    public ResponseEntity<MedicineRecord> createMedicineRecord(@RequestBody MedicineRecord medicineRecord) {
        MedicineRecord createdMedicineRecord = medicineRecordService.createMedicineRecord(medicineRecord);
        return ResponseEntity.ok(createdMedicineRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineRecord> getMedicineRecordById(@PathVariable Integer id) {
        Optional<MedicineRecord> medicineRecord = medicineRecordService.getMedicineRecordById(id);
        return medicineRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MedicineRecord>> getAllMedicineRecords() {
        List<MedicineRecord> medicineRecords = medicineRecordService.getAllMedicineRecords();
        return ResponseEntity.ok(medicineRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineRecord> updateMedicineRecord(@PathVariable Integer id, @RequestBody MedicineRecord medicineRecord) {
        medicineRecord.setId(id);
        MedicineRecord updatedMedicineRecord = medicineRecordService.updateMedicineRecord(medicineRecord);
        return ResponseEntity.ok(updatedMedicineRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineRecord(@PathVariable Integer id) {
        medicineRecordService.deleteMedicineRecord(id);
        return ResponseEntity.noContent().build();
    }
}