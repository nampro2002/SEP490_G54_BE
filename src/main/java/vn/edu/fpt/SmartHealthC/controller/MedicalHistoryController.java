package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
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
    public ApiResponse<MedicalHistory> createMedicalHistory(@RequestBody MedicalHistoryRequestDTO medicalHistory) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicalHistory>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicalHistoryService.createMedicalHistory(medicalHistory))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicalHistory> getMedicalHistoryById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalHistory>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.getMedicalHistoryById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<MedicalHistory>> getAllMedicalHistory() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicalHistory>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.getAllMedicalHistory())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalHistory> updateMedicalHistory(@RequestBody MedicalHistoryRequestDTO medicalHistory) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalHistory>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.updateMedicalHistory(medicalHistory))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicalHistory> deleteChronicDisease(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalHistory>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.deleteMedicalHistory(id))
                        .build()).getBody();
    }
}
