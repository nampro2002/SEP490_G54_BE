package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
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
    public ApiResponse<MedicineType> createMedicineType(@RequestBody MedicineType medicineType) {
        MedicineType createdMedicineType = medicineTypeService.createMedicineType(medicineType);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicineType>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdMedicineType)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineType>  getMedicineTypeById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineType>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.getMedicineTypeById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<MedicineType>> getAllMedicineTypes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicineType>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.getAllMedicineTypes())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicineType> updateMedicineType(@PathVariable Integer id,@RequestBody MedicineType medicineType) {
        MedicineType updatedMedicineType = medicineTypeService.updateMedicineType(id,medicineType);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineType>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedMedicineType)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicineType> deleteMedicineType(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineType>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.deleteMedicineType(id))
                        .build()).getBody();
    }
}
