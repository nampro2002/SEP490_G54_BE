package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicine-records")
public class MedicineRecordController {

    @Autowired
    private MedicineRecordService medicineRecordService;
    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping
    public ResponseEntity<MedicineRecord> createMedicineRecord(@RequestBody MedicineRecordDTO medicineRecordDTO) {

        MedicineRecord createdMedicineRecord = medicineRecordService.createMedicineRecord(medicineRecordDTO);
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
    public ResponseEntity<MedicineRecord> updateMedicineRecord(@PathVariable Integer id, @RequestBody MedicineRecordDTO medicineRecordDTO) {
        medicineRecordDTO.setId(id);
        MedicineRecord updatedMedicineRecord = medicineRecordService.updateMedicineRecord(medicineRecordDTO);
        return ResponseEntity.ok(updatedMedicineRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineRecord(@PathVariable Integer id) {
        medicineRecordService.deleteMedicineRecord(id);
        return ResponseEntity.noContent().build();
    }
}