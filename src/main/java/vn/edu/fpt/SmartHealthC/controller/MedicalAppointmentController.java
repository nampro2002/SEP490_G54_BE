package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentByWebUserDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ListPatientResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalAppointmentResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medical-appointment")
public class MedicalAppointmentController {

    @Autowired
    private MedicalAppointmentService medicalAppointmentService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<MedicalAppointmentResponseDTO> createMedicalAppointment(@RequestBody @Valid MedicalAppointmentDTO medicalAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicalAppointmentResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicalAppointmentService.createMedicalAppointment(medicalAppointmentDTO))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('MEDICAL_SPECIALIST')")
    @PostMapping("/web")
    public ApiResponse<MedicalAppointmentResponseDTO> createMedicalAppointmentByWebUser(@RequestBody @Valid MedicalAppointmentByWebUserDTO medicalAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicalAppointmentResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicalAppointmentService.createMedicalAppointmentByWebUser(medicalAppointmentDTO))
                        .build()).getBody();
    }
    @GetMapping("detail/{id}")
    public ApiResponse<MedicalAppointmentResponseDTO> getMedicalAppointmentById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalAppointmentResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getMedicalAppointmentById(id))
                        .build()).getBody();
    }
    @GetMapping("web/list-patient")
    public ApiResponse<List<ListPatientResponseDTO>> getListPatientByWebUserId() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ListPatientResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getListPatientByWebUserId())
                        .build()).getBody();
    }

    @GetMapping("/mobile")
    public ApiResponse<List<MedicalAppointmentResponseDTO>> getMedicalAppointmentByUserIdMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicalAppointmentResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getMedicalAppointmentByUserIdMobile())
                        .build()).getBody();
    }
    @GetMapping("/web/by-app-user/{id}")
    public ApiResponse<ResponsePaging<List<MedicalAppointmentResponseDTO>>> getMedicalAppointmentByUserId(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MedicalAppointmentResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getMedicalAppointmentByUserId(id, pageNo-1))
                        .build()).getBody();
    }

    @GetMapping("/web/all")
    public ApiResponse<ResponsePaging<List<MedicalAppointmentResponseDTO>>> getAllMedicalAppointments(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MedicalAppointmentResponseDTO>>  >builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getAllMedicalAppointments(pageNo - 1, search))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalAppointmentResponseDTO> updateMedicalAppointment(@PathVariable Integer id, @RequestBody @Valid MedicalAppointmentUpdateDTO medicalAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalAppointmentResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.updateMedicalAppointment(id, medicalAppointmentDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicalAppointmentResponseDTO> deleteMedicalAppointment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalAppointmentResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.deleteMedicalAppointment(id))
                        .build()).getBody();
    }
}