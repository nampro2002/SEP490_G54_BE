package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypeRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineTypeResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
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
    public ApiResponse<MedicineTypeResponseDTO> createMedicineType(@RequestBody @Valid MedicineTypeRequestDTO medicineType) {
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicineTypeResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicineTypeService.createMedicineType(medicineType))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineTypeResponseDTO>  getMedicineTypeById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineTypeResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.getMedicineTypeById(id))
                        .build()).getBody();
    }

    @GetMapping("/web/others")
    public ApiResponse<ResponsePaging<List<MedicineTypeResponseDTO>>> getAllMedicineTypes(@RequestParam(defaultValue = "1") Integer pageNo,  @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MedicineTypeResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.getAllMedicineTypes(pageNo-1, search))
                        .build()).getBody();
    }
    @GetMapping("/mobile")
    public ApiResponse<List<MedicineTypeResponseDTO>> getAllMedicineTypesMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicineTypeResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.getAllMedicineTypesMobile())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicineTypeResponseDTO> updateMedicineType(@PathVariable Integer id,@RequestBody @Valid MedicineTypeRequestDTO medicineType) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineTypeResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.updateMedicineType(id,medicineType))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicineTypeResponseDTO> deleteMedicineType(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineTypeResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineTypeService.deleteMedicineType(id))
                        .build()).getBody();
    }
}
