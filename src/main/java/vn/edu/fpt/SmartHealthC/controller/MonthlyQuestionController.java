package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
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
    public ApiResponse<MonthlyQuestion> createMonthlyQuestion(@RequestBody MonthlyQuestionDTO monthlyQuestionDTO) {

         MonthlyQuestion createdMonthlyQuestion=  monthlyQuestionService.createMonthlyQuestion(monthlyQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MonthlyQuestion>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdMonthlyQuestion)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse< MonthlyQuestion> getMonthlyQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MonthlyQuestion>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getMonthlyQuestionById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List< MonthlyQuestion>> getAllMonthlyQuestions() {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List< MonthlyQuestion>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getAllMonthlyQuestions())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse< MonthlyQuestion> updateMonthlyQuestion(@PathVariable Integer id, @RequestBody  MonthlyQuestionDTO monthlyQuestionDTO) {
         MonthlyQuestion updatedMonthlyQuestion =  monthlyQuestionService.updateMonthlyQuestion(id,monthlyQuestionDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MonthlyQuestion>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedMonthlyQuestion)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MonthlyQuestion> deleteMonthlyQuestion(@PathVariable Integer id) {
         monthlyQuestionService.deleteMonthlyQuestion(id);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MonthlyQuestion>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.deleteMonthlyQuestion(id))
                        .build()).getBody();
    }
}