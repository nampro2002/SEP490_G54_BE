package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/form-question")
public class FormQuestionController {
    @Autowired
    private FormQuestionService formQuestionService;

    @PostMapping
    public ResponseEntity<FormQuestion> createFormQuestion(@RequestBody FormQuestionRequestDTO formQuestion) {
        FormQuestion createdFormQuestion = formQuestionService.createFormQuestion(formQuestion);
        return ResponseEntity.ok(createdFormQuestion);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getFormQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FormQuestion>builder()
                        .result(formQuestionService.getFormQuestionById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ResponseEntity<List<FormQuestion>> getAllFormQuestions() {
        List<FormQuestion> formQuestions = formQuestionService.getAllFormQuestions();
        return ResponseEntity.ok(formQuestions);
    }

    @PutMapping()
    public ResponseEntity<FormQuestion> updateFormQuestion(@RequestBody FormQuestionRequestDTO formQuestion) {
        FormQuestion updatedformQuestion= formQuestionService.updateFormQuestion(formQuestion);
        return ResponseEntity.ok(updatedformQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormQuestion(@PathVariable Integer id) {
        formQuestionService.deleteFormQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
