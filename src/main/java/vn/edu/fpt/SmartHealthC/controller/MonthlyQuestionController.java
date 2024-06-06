package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthly-question")
public class MonthlyQuestionController {

    @Autowired
    private MonthlyQuestionService monthlyQuestionService;

    @PostMapping
    public ResponseEntity<MonthlyQuestion> createMonthlyQuestion(@RequestBody MonthlyQuestionDTO monthlyQuestionDTO) {

         MonthlyQuestion createdMonthlyQuestion=  monthlyQuestionService.createMonthlyQuestion(monthlyQuestionDTO);
        return ResponseEntity.ok(createdMonthlyQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity< MonthlyQuestion> getMonthlyQuestionById(@PathVariable Integer id) {
        Optional< MonthlyQuestion> monthlyQuestion =  monthlyQuestionService.getMonthlyQuestionById(id);
        return monthlyQuestion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List< MonthlyQuestion>> getAllMonthlyQuestions() {
        List< MonthlyQuestion> monthlyQuestions =  monthlyQuestionService.getAllMonthlyQuestions();
        return ResponseEntity.ok(monthlyQuestions);
    }

    @PutMapping("/{id}")
    public ResponseEntity< MonthlyQuestion> updateMonthlyQuestion(@PathVariable Integer id, @RequestBody  MonthlyQuestionDTO monthlyQuestionDTO) {
        monthlyQuestionDTO.setId(id);
         MonthlyQuestion updatedMonthlyQuestion =  monthlyQuestionService.updateMonthlyQuestion(monthlyQuestionDTO);
        return ResponseEntity.ok(updatedMonthlyQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonthlyQuestion(@PathVariable Integer id) {
         monthlyQuestionService.deleteMonthlyQuestion(id);
        return ResponseEntity.noContent().build();
    }
}