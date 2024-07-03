package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AnswerQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.QuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.serivce.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<QuestionResponseDTO> createQuestion(@RequestBody @Valid QuestionRequestDTO questionRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(questionService.createQuestion(questionRequestDTO))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/detail/{id}")
    public ApiResponse<QuestionResponseDTO> getQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionById(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public ApiResponse<List<QuestionResponseDTO>> getQuestionByUserId() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionByAppUserId())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/web/admin/pending")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsPendingAd() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getAllPendingQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/ms/pending")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsPendingMs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getAllPendingQuestionsByType(TypeUserQuestion.ASSIGN_MS))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/web/admin/all")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsAd() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/ms/all")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsMs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionsByType(TypeUserQuestion.ASSIGN_MS))
                        .build()).getBody();
    }
    //answer question
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MEDICAL_SPECIALIST')")
    @PutMapping("/answer/{id}")
    public ApiResponse<QuestionResponseDTO> answerQuestion(@PathVariable Integer id,@RequestBody @Valid AnswerQuestionRequestDTO answer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.updateQuestion(id,answer))
                        .build()).getBody();
    }
    //test only
    @PutMapping("/removeAnswer/{id}")
    public ApiResponse<QuestionResponseDTO> removeAnswer(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.removeAnswer(id))
                        .build()).getBody();
    }
    //test only
    @DeleteMapping("/{id}")
    public ApiResponse<QuestionResponseDTO> deleteQuestion(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.deleteQuestion(id))
                        .build()).getBody();
    }
}