package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<FormQuestion> createFormQuestion(@RequestBody FormQuestion formQuestion) {
        FormQuestion createdFormQuestion = formQuestionService.createFormQuestion(formQuestion);
        return ResponseEntity.ok(createdFormQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormQuestion> getFormQuestionById(@PathVariable Integer id) {
        Optional<FormQuestion> formQuestion = formQuestionService.getFormQuestionById(id);
        return formQuestion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FormQuestion>> getAllFormQuestions() {
        List<FormQuestion> formQuestions = formQuestionService.getAllFormQuestions();
        return ResponseEntity.ok(formQuestions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormQuestion> updateFormQuestion(@PathVariable Integer id, @RequestBody FormQuestion formQuestion) {
        formQuestion.setId(id);
        FormQuestion updatedformQuestion= formQuestionService.updateFormQuestion(formQuestion);
        return ResponseEntity.ok(updatedformQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormQuestion(@PathVariable Integer id) {
        formQuestionService.deleteFormQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
