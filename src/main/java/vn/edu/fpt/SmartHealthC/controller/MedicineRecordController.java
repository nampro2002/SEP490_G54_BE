package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
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
    public ApiResponse<MedicineRecord>  createMedicineRecord(@RequestBody MedicineRecordDTO medicineRecordDTO) {

        MedicineRecord createdMedicineRecord = medicineRecordService.createMedicineRecord(medicineRecordDTO);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicineRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdMedicineRecord)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineRecord> getMedicineRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getMedicineRecordById(id))
                        .build()).getBody();
    }

    @GetMapping
    public  ApiResponse<List<MedicineRecord>> getAllMedicineRecords() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicineRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getAllMedicineRecords())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicineRecord> updateMedicineRecord(@PathVariable Integer id,@RequestBody MedicineRecordDTO medicineRecordDTO) {
        MedicineRecord updateMedicineRecord = medicineRecordService.updateMedicineRecord(id,medicineRecordDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updateMedicineRecord)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicineRecord> deleteMedicineRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.deleteMedicineRecord(id))
                        .build()).getBody();
    }
}