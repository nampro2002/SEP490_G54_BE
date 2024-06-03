package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.UserChronicDisease;
import vn.edu.fpt.SmartHealthC.serivce.UserChronicDiseaseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-chronic-diseases")
public class UserChronicDiseaseController {
    @Autowired
    private UserChronicDiseaseService userChronicDiseaseService;

    @PostMapping
    public ResponseEntity<UserChronicDisease> createUserChronicDisease(@RequestBody UserChronicDisease userChronicDisease) {
        UserChronicDisease createdUserChronicDisease = userChronicDiseaseService.createUserChronicDisease(userChronicDisease);
        return ResponseEntity.ok(createdUserChronicDisease);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserChronicDisease> getUserChronicDiseaseById(@PathVariable Integer id) {
        Optional<UserChronicDisease> userChronicDisease = userChronicDiseaseService.getUserChronicDiseaseById(id);
        return userChronicDisease.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserChronicDisease>> getAllUserChronicDiseases() {
        List<UserChronicDisease> userChronicDiseases = userChronicDiseaseService.getAllUserChronicDiseases();
        return ResponseEntity.ok(userChronicDiseases);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserChronicDisease> updateUserChronicDisease(@PathVariable Integer id, @RequestBody UserChronicDisease userChronicDisease) {
        userChronicDisease.setId(id);
        UserChronicDisease updatedUserChronicDisease = userChronicDiseaseService.updateUserChronicDisease(userChronicDisease);
        return ResponseEntity.ok(updatedUserChronicDisease);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserChronicDisease(@PathVariable Integer id) {
        userChronicDiseaseService.deleteUserChronicDisease(id);
        return ResponseEntity.noContent().build();
    }
}
