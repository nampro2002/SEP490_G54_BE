package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;
import vn.edu.fpt.SmartHealthC.serivce.UserMedicalHistoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-chronic-diseases")
public class UserMedicalHistoryController {
    @Autowired
    private UserMedicalHistoryService userMedicalHistoryService;

    @PostMapping
    public ResponseEntity<UserMedicalHistory> createUserMedicalHistory(@RequestBody UserMedicalHistory userMedicalHistory) {
        UserMedicalHistory createdUserMedicalHistory = userMedicalHistoryService.createUserMedicalHistory(userMedicalHistory);
        return ResponseEntity.ok(createdUserMedicalHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMedicalHistory> getUserMedicalHistoryById(@PathVariable Integer id) {
        Optional<UserMedicalHistory> userMedicalHistory = userMedicalHistoryService.getUserMedicalHistoryById(id);
        return userMedicalHistory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserMedicalHistory>> getAllUserMedicalHistory() {
        List<UserMedicalHistory> userMedicalHistories = userMedicalHistoryService.getAllUserMedicalHistory();
        return ResponseEntity.ok(userMedicalHistories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMedicalHistory> updateUserMedicalHistory(@PathVariable Integer id, @RequestBody UserMedicalHistory userMedicalHistory) {
        userMedicalHistory.setId(id);
        UserMedicalHistory updatedUserMedicalHistory = userMedicalHistoryService.updateUserMedicalHistory(userMedicalHistory);
        return ResponseEntity.ok(updatedUserMedicalHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserMedicalHistory(@PathVariable Integer id) {
        userMedicalHistoryService.deleteUserMedicalHistory(id);
        return ResponseEntity.noContent().build();
    }
}
