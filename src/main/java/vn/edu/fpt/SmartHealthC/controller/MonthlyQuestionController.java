package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyAnswerResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyNumberResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-question")
public class MonthlyQuestionController {

    @Autowired
    private MonthlyQuestionService monthlyQuestionService;

    @PostMapping
    public ApiResponse<Void> create40MonthlyQuestion(@RequestBody @Valid List<MonthlyQuestionDTO> monthlyQuestionDTO) {
        monthlyQuestionService.create40MonthlyQuestion(monthlyQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }
    @PostMapping("/test")
    public ApiResponse<Void> createMonthlyQuestion() {
        monthlyQuestionService.createNewMonthMark(13);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }
    @GetMapping("/list-month-number")
    public ApiResponse<List<MonthlyNumberResponseDTO>> getListMonthlyNumber() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyNumberResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getList3MonthlyNumber())
                        .build()).getBody();
    }
    @GetMapping("/mobile/get-answer/{monthNumber}")
    public ApiResponse<List<MonthlyAnswerResponseDTO>> getMobileListAnswer(
            @PathVariable int monthNumber
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyAnswerResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getMobileListAnswer(monthNumber))
                        .build()).getBody();
    }
    @GetMapping("/web/get-answer/{userId}/{monthNumber}")
    public ApiResponse<List<MonthlyAnswerResponseDTO>> getWebListAnswer(
            @PathVariable int userId,
            @PathVariable int monthNumber
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyAnswerResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getWebListAnswer(userId,monthNumber))
                        .build()).getBody();
    }

}