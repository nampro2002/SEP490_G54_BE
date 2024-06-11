package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserMedicalHistoryDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
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
    public ApiResponse<UserMedicalHistory> createUserMedicalHistory(@RequestBody UserMedicalHistoryDTO userMedicalHistoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserMedicalHistory>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(userMedicalHistoryService.createUserMedicalHistory(userMedicalHistoryDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserMedicalHistory> getUserMedicalHistoryById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserMedicalHistory>builder()
                        .code(HttpStatus.OK.value())
                        .result(userMedicalHistoryService.getUserMedicalHistoryById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<UserMedicalHistory>> getAllUserMedicalHistory() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<UserMedicalHistory>>builder()
                        .code(HttpStatus.OK.value())
                        .result(userMedicalHistoryService.getAllUserMedicalHistory())
                        .build()).getBody();
    }

//    @PutMapping("/{id}")
//    public ApiResponse<UserMedicalHistory> updateUserMedicalHistory(@PathVariable Integer id, @RequestBody UserMedicalHistoryDTO userMedicalHistoryDTO) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<UserMedicalHistory>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(userMedicalHistoryService.updateUserMedicalHistory(id, userMedicalHistoryDTO))
//                        .build()).getBody();
//    }

    @DeleteMapping("/{id}")
    public ApiResponse<UserMedicalHistory> deleteUserMedicalHistory(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserMedicalHistory>builder()
                        .code(HttpStatus.OK.value())
                        .result(userMedicalHistoryService.deleteUserMedicalHistory(id))
                        .build()).getBody();
    }
}
