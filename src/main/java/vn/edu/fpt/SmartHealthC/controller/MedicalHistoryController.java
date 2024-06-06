package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.serivce.MedicalHistoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chronic-diseases")
public class MedicalHistoryController {
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    @PostMapping
    public ResponseEntity<MedicalHistory> createMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
        MedicalHistory createdMedicalHistory = medicalHistoryService.createMedicalHistory(medicalHistory);
        return ResponseEntity.ok(createdMedicalHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistory> getMedicalHistoryById(@PathVariable Integer id) {
        Optional<MedicalHistory> chronicDisease = medicalHistoryService.getMedicalHistoryById(id);
        return chronicDisease.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MedicalHistory>> getAllMedicalHistory() {
        List<MedicalHistory> medicalHistories = medicalHistoryService.getAllMedicalHistory();
        return ResponseEntity.ok(medicalHistories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistory> updateMedicalHistory(@PathVariable Integer id, @RequestBody MedicalHistory medicalHistory) {
        medicalHistory.setId(id);
        MedicalHistory updatedMedicalHistory = medicalHistoryService.updateMedicalHistory(medicalHistory);
        return ResponseEntity.ok(updatedMedicalHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChronicDisease(@PathVariable Integer id) {
        medicalHistoryService.deleteMedicalHistory(id);
        return ResponseEntity.noContent().build();
    }
}
