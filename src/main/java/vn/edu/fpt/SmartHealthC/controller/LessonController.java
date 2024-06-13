package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.serivce.LessonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @PostMapping
    public ApiResponse<Lesson> createLesson(@RequestBody LessonRequestDTO lesson) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Lesson>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(lessonService.createLesson(lesson))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<Lesson> getLessonById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.getLessonById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<Lesson>> getAllLessons() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Lesson>>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.getAllLessons())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<Lesson> updateLesson(@PathVariable Integer id, @RequestBody LessonRequestDTO lessonRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.updateLesson(id,lessonRequestDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Lesson> deleteLesson(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.deleteLesson(id))
                        .build()).getBody();
    }
}
