package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
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
    public ApiResponse<MedicineRecordResponseDTO>  createMedicineRecord(@RequestBody @Valid MedicineRecordDTO medicineRecordDTO) {
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicineRecordResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicineRecordService.createMedicineRecord(medicineRecordDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineRecordResponseDTO> getMedicineRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getMedicineRecordById(id))
                        .build()).getBody();
    }

    @GetMapping("/web/weekly-record/{id}")
    public  ApiResponse<List<MedicineRecordListResDTO>> getAllMedicineRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicineRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getAllMedicineRecords(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicineRecordResponseDTO> updateMedicineRecord(@PathVariable Integer id,@RequestBody @Valid MedicineRecordDTO medicineRecordDTO) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.updateMedicineRecord(id,medicineRecordDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicineRecordResponseDTO> deleteMedicineRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.deleteMedicineRecord(id))
                        .build()).getBody();
    }
}