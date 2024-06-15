package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ApiResponse<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(questionService.createQuestion(questionRequestDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<QuestionResponseDTO> getQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionById(id))
                        .build()).getBody();
    }

    @GetMapping("/user")
    public ApiResponse<List<QuestionResponseDTO>> getQuestionByUserId(@RequestParam(defaultValue = "1") Integer userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionByAppUserId(userId))
                        .build()).getBody();
    }
    @GetMapping("/questionPAdmin")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsPendingAd() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getAllPendingQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN))
                        .build()).getBody();
    }
    @GetMapping("/questionPMs")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsPendingMs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getAllPendingQuestionsByType(TypeUserQuestion.ASSIGN_MS))
                        .build()).getBody();
    }

    @GetMapping("/questionAdmin")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsAd() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN))
                        .build()).getBody();
    }
    @GetMapping("/questionMs")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsMs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionsByType(TypeUserQuestion.ASSIGN_MS))
                        .build()).getBody();
    }


    //answer question
    @PutMapping("{id}")
    public ApiResponse<QuestionResponseDTO> answerQuestion(@PathVariable Integer id,@RequestBody AnswerQuestionRequestDTO answer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.updateQuestion(id,answer))
                        .build()).getBody();
    }
    @PutMapping("removeAnswer/{id}")
    public ApiResponse<QuestionResponseDTO> removeAnswer(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.removeAnswer(id))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<QuestionResponseDTO> deleteQuestion(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<QuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.deleteQuestion(id))
                        .build()).getBody();
    }
}