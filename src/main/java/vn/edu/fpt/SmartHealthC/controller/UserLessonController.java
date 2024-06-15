package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserLessonDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.serivce.UserLessonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-lessons")
public class UserLessonController {
    @Autowired
    private UserLessonService userLessonService;

    @PostMapping
    public ApiResponse<UserLesson> createUserLesson(@RequestBody @Valid UserLessonDTO userLessonDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserLesson>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(userLessonService.createUserLesson(userLessonDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public  ApiResponse<UserLesson> getUserLessonById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserLesson>builder()
                        .code(HttpStatus.OK.value())
                        .result(userLessonService.getUserLessonById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<UserLesson>> getAllUserLessons() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<UserLesson>>builder()
                        .code(HttpStatus.OK.value())
                        .result(userLessonService.getAllUserLessons())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserLesson> updateUserLesson(@PathVariable Integer id, @RequestBody @Valid UserLessonDTO userLessonDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserLesson>builder()
                        .code(HttpStatus.OK.value())
                        .result(userLessonService.updateUserLesson(id, userLessonDTO))
                        .build()).getBody();
    }
    // not use to delete
    @DeleteMapping("/{id}")
    public ApiResponse<UserLesson> deleteUserLesson(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserLesson>builder()
                        .code(HttpStatus.OK.value())
                        .result(userLessonService.deleteUserLesson(id))
                        .build()).getBody();
    }

}