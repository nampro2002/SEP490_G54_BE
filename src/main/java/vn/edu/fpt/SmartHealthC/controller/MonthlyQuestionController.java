package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-question")
public class MonthlyQuestionController {

    @Autowired
    private MonthlyQuestionService monthlyQuestionService;

    @PostMapping
    public ApiResponse<MonthlyRecord> createMonthlyQuestion(@RequestBody MonthlyQuestionDTO monthlyQuestionDTO) {

         MonthlyRecord createdMonthlyRecord =  monthlyQuestionService.createMonthlyQuestion(monthlyQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MonthlyRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdMonthlyRecord)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MonthlyRecord> getMonthlyQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MonthlyRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getMonthlyQuestionById(id))
                        .build()).getBody();
    }

    @GetMapping("/mobile")
    public ApiResponse<List<MonthlyRecord>> getAllMonthlyQuestionsMobile() {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getAllMonthlyQuestionsMobile())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MonthlyRecord> updateMonthlyQuestion(@PathVariable Integer id, @RequestBody  MonthlyQuestionDTO monthlyQuestionDTO) {
         MonthlyRecord updatedMonthlyRecord =  monthlyQuestionService.updateMonthlyQuestion(id,monthlyQuestionDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MonthlyRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedMonthlyRecord)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MonthlyRecord> deleteMonthlyQuestion(@PathVariable Integer id) {
         monthlyQuestionService.deleteMonthlyQuestion(id);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MonthlyRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.deleteMonthlyQuestion(id))
                        .build()).getBody();
    }
}