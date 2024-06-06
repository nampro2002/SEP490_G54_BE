package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
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
    public ResponseEntity<MedicalAppointment> createMedicalAppointment(@RequestBody MedicalAppointmentDTO medicalAppointmentDTO) {

        MedicalAppointment createdMedicalAppointment= medicalAppointmentService.createMedicalAppointment(medicalAppointmentDTO);
        return ResponseEntity.ok(createdMedicalAppointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalAppointment> getMedicalAppointmentById(@PathVariable Integer id) {
        Optional<MedicalAppointment> medicalAppointment = medicalAppointmentService.getMedicalAppointmentById(id);
        return medicalAppointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MedicalAppointment>> getAllMedicalAppointments() {
        List<MedicalAppointment> medicalAppointments = medicalAppointmentService.getAllMedicalAppointments();
        return ResponseEntity.ok(medicalAppointments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalAppointment> updateMedicalAppointment(@PathVariable Integer id, @RequestBody MedicalAppointmentDTO medicalAppointmentDTO) {
        medicalAppointmentDTO.setId(id);
        MedicalAppointment updatedMedicalAppointment = medicalAppointmentService.updateMedicalAppointment(medicalAppointmentDTO);
        return ResponseEntity.ok(updatedMedicalAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalAppointment(@PathVariable Integer id) {
        medicalAppointmentService.deleteMedicalAppointment(id);
        return ResponseEntity.noContent().build();
    }
}