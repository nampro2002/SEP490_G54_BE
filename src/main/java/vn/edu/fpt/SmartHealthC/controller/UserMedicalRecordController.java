package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalRecord;
import vn.edu.fpt.SmartHealthC.serivce.UserMedicalRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-medical-records")
public class UserMedicalRecordController {
    @Autowired
    private UserMedicalRecordService userMedicalRecordService;

    @PostMapping
    public ResponseEntity<UserMedicalRecord> createUserMedicalRecord(@RequestBody UserMedicalRecord userMedicalRecord) {
        UserMedicalRecord createdUserMedicalRecord = userMedicalRecordService.createUserMedicalRecord(userMedicalRecord);
        return ResponseEntity.ok(createdUserMedicalRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMedicalRecord> getUserMedicalRecordById(@PathVariable Integer id) {
        Optional<UserMedicalRecord> userMedicalRecord = userMedicalRecordService.getUserMedicalRecordById(id);
        return userMedicalRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserMedicalRecord>> getAllUserMedicalRecords() {
        List<UserMedicalRecord> userMedicalRecords = userMedicalRecordService.getAllUserMedicalRecords();
        return ResponseEntity.ok(userMedicalRecords);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMedicalRecord> updateUserMedicalRecord(@PathVariable Integer id, @RequestBody UserMedicalRecord userMedicalRecord) {
        userMedicalRecord.setId(id);
        UserMedicalRecord updatedUserMedicalRecord = userMedicalRecordService.updateUserMedicalRecord(userMedicalRecord);
        return ResponseEntity.ok(updatedUserMedicalRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserMedicalRecord(@PathVariable Integer id) {
        userMedicalRecordService.deleteUserMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
