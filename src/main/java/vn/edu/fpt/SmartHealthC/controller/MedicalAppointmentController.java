package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
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
    public ApiResponse<MedicalAppointment> createMedicalAppointment(@RequestBody MedicalAppointmentDTO medicalAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MedicalAppointment>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(medicalAppointmentService.createMedicalAppointment(medicalAppointmentDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicalAppointment> getMedicalAppointmentById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalAppointment>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getMedicalAppointmentById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<MedicalAppointment>> getAllMedicalAppointments() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicalAppointment>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getAllMedicalAppointments())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalAppointment> updateMedicalAppointment(@PathVariable Integer id, @RequestBody MedicalAppointmentDTO medicalAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalAppointment>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.updateMedicalAppointment(medicalAppointmentDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MedicalAppointment> deleteMedicalAppointment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicalAppointment>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.deleteMedicalAppointment(id))
                        .build()).getBody();
    }
}