package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.serivce.MedicalHistoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medical-history")
public class MedicalHistoryController {
    @Autowired
    private MedicalHistoryService medicalHistoryService;
    @PostMapping
    public ApiResponse<MedicalHistoryResDTO> createMedicalHistory(@RequestBody @Valid MedicalHistoryRequestDTO medicalHistory) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicalHistoryResDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicalHistoryService.createMedicalHistory(medicalHistory))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicalHistoryResDTO> getMedicalHistoryById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalHistoryResDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.getMedicalHistoryById(id))
                        .build()).getBody();
    }

    @GetMapping("/web/others")
    public ApiResponse<ResponsePaging<List<MedicalHistoryResDTO>>> getAllMedicalHistory(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MedicalHistoryResDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.getAllMedicalHistory(pageNo - 1, search))
                        .build()).getBody();
    }
    @GetMapping("/mobile")
    public ApiResponse<List<MedicalHistoryResDTO>> getAllMedicalHistoryMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicalHistoryResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.getAllMedicalHistoryMobile())
                        .build()).getBody();
    }


    @PutMapping("/{id}")
    public ApiResponse<MedicalHistoryResDTO> updateMedicalHistory(@PathVariable Integer id, @RequestBody @Valid MedicalHistoryRequestDTO medicalHistory) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalHistoryResDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.updateMedicalHistory(id, medicalHistory))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicalHistoryResDTO> deleteMedicalHistory(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalHistoryResDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalHistoryService.deleteMedicalHistory(id))
                        .build()).getBody();
    }
}
