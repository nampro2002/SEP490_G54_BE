package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypePlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineTypePlan;
import vn.edu.fpt.SmartHealthC.domain.entity.RuleForPlan;
import vn.edu.fpt.SmartHealthC.serivce.MedicineTypePlanService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicine-type-plan")
public class MedicineTypePlanController {

    @Autowired
    private MedicineTypePlanService medicineTypePlanService;

    @PostMapping
    public ResponseEntity<MedicineTypePlan> createMedicineTypePlan(@RequestBody MedicineTypePlanDTO medicineTypePlanDTO) {

        MedicineTypePlan createdMedicineTypePlan= medicineTypePlanService.createMedicineTypePlan(medicineTypePlanDTO);
        return ResponseEntity.ok(createdMedicineTypePlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineTypePlan> getMedicineTypePlanById(@PathVariable Integer id) {
        Optional<MedicineTypePlan> medicineTypePlan = medicineTypePlanService.getMedicineTypePlanById(id);
        return medicineTypePlan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MedicineTypePlan>> getAllMedicineTypePlans() {
        List<MedicineTypePlan> medicineTypePlans = medicineTypePlanService.getAllMedicineTypePlans();
        return ResponseEntity.ok(medicineTypePlans);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineTypePlan> updateMedicineTypePlan(@PathVariable Integer id, @RequestBody MedicineTypePlanDTO medicineTypePlanDTO) {
        medicineTypePlanDTO.setId(id);
        MedicineTypePlan updatedMedicineTypePlan = medicineTypePlanService.updateMedicineTypePlan(medicineTypePlanDTO);
        return ResponseEntity.ok(updatedMedicineTypePlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineTypePlan(@PathVariable Integer id) {
        medicineTypePlanService.deleteMedicineTypePlan(id);
        return ResponseEntity.noContent().build();
    }
}