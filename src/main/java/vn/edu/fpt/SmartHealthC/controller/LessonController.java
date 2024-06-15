package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.LessonResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
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
    public ApiResponse<LessonResponseDTO> createLesson(@RequestBody @Valid LessonRequestDTO lesson) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<LessonResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(lessonService.createLesson(lesson))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonResponseDTO> getLessonById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<LessonResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.getLessonById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<ResponsePaging<List<LessonResponseDTO>>> getAllLessons(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<LessonResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.getAllLessons(pageNo-1, search))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<LessonResponseDTO> updateLesson(@PathVariable Integer id, @RequestBody @Valid LessonRequestDTO lessonRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<LessonResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.updateLesson(id,lessonRequestDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<LessonResponseDTO> deleteLesson(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<LessonResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(lessonService.deleteLesson(id))
                        .build()).getBody();
    }
}
