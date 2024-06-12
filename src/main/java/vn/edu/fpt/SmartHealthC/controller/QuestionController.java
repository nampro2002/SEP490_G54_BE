package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.request.QuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.QuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ApiResponse<Question> createQuestion(@RequestBody QuestionDTO questionDTO) {

        Question createdQuestion= questionService.createQuestion(questionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Question>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdQuestion)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<Question> getQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Question>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getQuestionById(id))
                        .build()).getBody();
    }

    @GetMapping("/questionAdmin")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsAd() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getAllPendingQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN))
                        .build()).getBody();
    }
    @GetMapping("/questionMs")
    public ApiResponse<List<QuestionResponseDTO>> getAllQuestionsMs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(questionService.getAllPendingQuestionsByType(TypeUserQuestion.ASSIGN_MS))
                        .build()).getBody();
    }
    //answer question
    @PutMapping("/{id}")
    public ApiResponse<Question> updateQuestion(@PathVariable Integer id,@RequestBody String answer) {
        Question updatedQuestion = questionService.updateQuestion(id,answer);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Question>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedQuestion)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Question> deleteQuestion(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Question>builder()
                        .code(HttpStatus.OK.value())
                        .result(  questionService.deleteQuestion(id))
                        .build()).getBody();
    }
}