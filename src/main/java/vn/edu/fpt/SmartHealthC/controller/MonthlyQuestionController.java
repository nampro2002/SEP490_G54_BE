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
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyStatisticResponseDTO;
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
        monthlyQuestionService.createAnswers(monthlyQuestionDTO);
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

    //Lấy 3 tháng gần nhất w.monthlyRecordType = 'NEW_MONTH_MARK' (chưa trả lời)
    @GetMapping("/mobile/list-month-number")
    public ApiResponse<List<MonthlyNumberResponseDTO>> getListMonthlyNumber() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyNumberResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getList3MonthlyNumber())
                        .build()).getBody();
    }
     // Lấy 3 tháng gần nhất w.monthlyRecordType != 'NEW_MONTH_MARK' (đã trả lời)
    @GetMapping("/web/get-3-month/{appUserId}")
    public ApiResponse<List<Integer>> getList3MonthlyNumberWeb( @PathVariable Integer appUserId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Integer>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getList3MonthlyNumberWeb(appUserId))
                        .build()).getBody();

    }

    //Lấy câu trả lời theo tháng
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


    //lấy chart của 3 tháng gần nhất
    @GetMapping("/mobile/get-chart")
    public ApiResponse<List<MonthlyStatisticResponseDTO>> getPoint3MonthMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyStatisticResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getPoint3MonthMobile())
                        .build()).getBody();
    }
    //lấy chart của 12 tháng
    @GetMapping("/web/get-chart/{appUserId}")
    public ApiResponse<List<MonthlyStatisticResponseDTO>> getPoint12MonthWeb(
            @PathVariable Integer appUserId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyStatisticResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getPoint12MonthWeb(appUserId))
                        .build()).getBody();
    }
    //Lấy chart chi tiết của 2 tháng gần với tháng nhập vào
    @GetMapping("/mobile/get-detail/{monthNumber}")
    public ApiResponse<List<MonthlyStatisticResponseDTO>> getPoint2MonthMobile( @PathVariable Integer monthNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyStatisticResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getPoint2MonthMobile(monthNumber))
                        .build()).getBody();

    }
    @GetMapping("/web/get-2-month/{appUserId}/{monthNumber}")
    public ApiResponse<List<MonthlyStatisticResponseDTO>> getPoint2MonthWeb(
            @PathVariable Integer appUserId,
            @PathVariable Integer monthNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyStatisticResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getPoint2MonthWeb(appUserId,monthNumber))
                        .build()).getBody();

    }



}