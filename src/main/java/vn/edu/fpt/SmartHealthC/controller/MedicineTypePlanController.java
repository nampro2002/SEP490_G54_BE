package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypePlanDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
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
    public ApiResponse<MedicineTypePlan> createMedicineTypePlan(@RequestBody MedicineTypePlanDTO medicineTypePlanDTO) {

        MedicineTypePlan createdMedicineTypePlan= medicineTypePlanService.createMedicineTypePlan(medicineTypePlanDTO);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicineTypePlan>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdMedicineTypePlan)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineTypePlan> getMedicineTypePlanById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineTypePlan>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypePlanService.getMedicineTypePlanById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<MedicineTypePlan>> getAllMedicineTypePlans() {
        List<MedicineTypePlan> medicineTypePlans = medicineTypePlanService.getAllMedicineTypePlans();
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicineTypePlan>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypePlanService.getAllMedicineTypePlans())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicineTypePlan> updateMedicineTypePlan(@PathVariable Integer id,@RequestBody MedicineTypePlanDTO medicineTypePlanDTO) {
        MedicineTypePlan updatedMedicineTypePlan = medicineTypePlanService.updateMedicineTypePlan(id,medicineTypePlanDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineTypePlan>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedMedicineTypePlan)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicineTypePlan> deleteMedicineTypePlan(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineTypePlan>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypePlanService.deleteMedicineTypePlan(id))
                        .build()).getBody();
    }
}