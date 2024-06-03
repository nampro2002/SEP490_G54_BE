package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;
import vn.edu.fpt.SmartHealthC.serivce.MedicineTypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicine-types")
public class MedicineTypeController {
    @Autowired
    private MedicineTypeService medicineTypeService;

    @PostMapping
    public ResponseEntity<MedicineType> createMedicineType(@RequestBody MedicineType medicineType) {
        MedicineType createdMedicineType = medicineTypeService.createMedicineType(medicineType);
        return ResponseEntity.ok(createdMedicineType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineType> getMedicineTypeById(@PathVariable Integer id) {
        Optional<MedicineType> medicineType = medicineTypeService.getMedicineTypeById(id);
        return medicineType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MedicineType>> getAllMedicineTypes() {
        List<MedicineType> medicineTypes = medicineTypeService.getAllMedicineTypes();
        return ResponseEntity.ok(medicineTypes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineType> updateMedicineType(@PathVariable Integer id, @RequestBody MedicineType medicineType) {
        medicineType.setId(id);
        MedicineType updatedMedicineType = medicineTypeService.updateMedicineType(medicineType);
        return ResponseEntity.ok(updatedMedicineType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineType(@PathVariable Integer id) {
        medicineTypeService.deleteMedicineType(id);
        return ResponseEntity.noContent().build();
    }
}
