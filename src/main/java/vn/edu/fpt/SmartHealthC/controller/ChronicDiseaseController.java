package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.ChronicDisease;
import vn.edu.fpt.SmartHealthC.serivce.ChronicDiseaseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chronic-diseases")
public class ChronicDiseaseController {
    @Autowired
    private ChronicDiseaseService chronicDiseaseService;
    @PostMapping
    public ResponseEntity<ChronicDisease> createChronicDisease(@RequestBody ChronicDisease chronicDisease) {
        ChronicDisease createdChronicDisease = chronicDiseaseService.createChronicDisease(chronicDisease);
        return ResponseEntity.ok(createdChronicDisease);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChronicDisease> getChronicDiseaseById(@PathVariable Integer id) {
        Optional<ChronicDisease> chronicDisease = chronicDiseaseService.getChronicDiseaseById(id);
        return chronicDisease.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChronicDisease>> getAllChronicDiseases() {
        List<ChronicDisease> chronicDiseases = chronicDiseaseService.getAllChronicDiseases();
        return ResponseEntity.ok(chronicDiseases);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChronicDisease> updateChronicDisease(@PathVariable Integer id, @RequestBody ChronicDisease chronicDisease) {
        chronicDisease.setId(id);
        ChronicDisease updatedChronicDisease = chronicDiseaseService.updateChronicDisease(chronicDisease);
        return ResponseEntity.ok(updatedChronicDisease);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChronicDisease(@PathVariable Integer id) {
        chronicDiseaseService.deleteChronicDisease(id);
        return ResponseEntity.noContent().build();
    }
}
