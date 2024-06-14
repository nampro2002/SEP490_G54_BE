package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
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

    @PostMapping
    public ApiResponse<MedicalAppointmentResponseDTO> createMedicalAppointment(@RequestBody MedicalAppointmentDTO medicalAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicalAppointmentResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicalAppointmentService.createMedicalAppointment(medicalAppointmentDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicalAppointmentResponseDTO> getMedicalAppointmentById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalAppointmentResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getMedicalAppointmentById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<ResponsePaging<List<MedicalAppointmentResponseDTO>>> getAllMedicalAppointments(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MedicalAppointmentResponseDTO>>  >builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getAllMedicalAppointments(pageNo - 1, search))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalAppointmentResponseDTO> updateMedicalAppointment(@PathVariable Integer id, @RequestBody MedicalAppointmentDTO medicalAppointmentDTO) {
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