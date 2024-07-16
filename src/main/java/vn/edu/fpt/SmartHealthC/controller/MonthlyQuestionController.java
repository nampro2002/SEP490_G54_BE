package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.MobileScreen2MonthChartSATResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.MobileScreen2MonthChartSFResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.MobileScreenTotalChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyAnswerResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyNumberResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyStatisticResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.MonthlyStatisticScreenTotalResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.MonthlyStatisticScreenTotalSATResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.MonthlyStatisticScreenTotalSFResponseDTO;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;
import vn.edu.fpt.SmartHealthC.utils.FormatDTOUtils;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-question")
public class MonthlyQuestionController {

    @Autowired
    private MonthlyQuestionService monthlyQuestionService;

    @PostMapping
    public ApiResponse<Void> createMonthlyAnswer(@RequestBody @Valid List<MonthlyQuestionDTO> monthlyQuestionDTO) {
        monthlyQuestionService.createAnswers(monthlyQuestionDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }
//    @PostMapping("/test")
//    public ApiResponse<Void> createMonthlyQuestion() {
//        monthlyQuestionService.createNewMonthMark(13);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ApiResponse.<Void>builder()
//                        .code(HttpStatus.CREATED.value())
//                        .result(null)
//                        .build()).getBody();
//    }

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
    @GetMapping("/mobile/get-answer/{monthNumber}/{type}")
    public ApiResponse<List<MonthlyAnswerResponseDTO>> getMobileListAnswer(
            @PathVariable int monthNumber,
            @PathVariable String type
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyAnswerResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getMobileListAnswer(monthNumber,type))
                        .build()).getBody();
    }
    @GetMapping("/web/get-answer/{userId}/{monthNumber}/{type}")
    public ApiResponse<List<MonthlyAnswerResponseDTO>> getWebListAnswer(
            @PathVariable int userId,
            @PathVariable int monthNumber,
               @PathVariable String type
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyAnswerResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(monthlyQuestionService.getWebListAnswer(userId,monthNumber,type))
                        .build()).getBody();
    }


    //lấy chart của 3 tháng gần nhất
    //Screen total
    @GetMapping("/mobile/screen-total/get-chart")
    public ApiResponse<MobileScreenTotalChartResponseDTO> getScreenTotalPoint3MonthMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MobileScreenTotalChartResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(FormatDTOUtils.formatMobileScreenTotal(monthlyQuestionService.getPoint3MonthMobile()))
                        .build()).getBody();
    }

    //lấy chart của 12 tháng
    //Screen total
    @GetMapping("/web/screen-total-sat/get-chart/{appUserId}")
    public ApiResponse<List<MonthlyStatisticScreenTotalSATResponseDTO>> getPoint12MonthWebSAT(
            @PathVariable Integer appUserId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyStatisticScreenTotalSATResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(FormatDTOUtils.formatWebScreenTotalSAT(monthlyQuestionService.getPoint12MonthWeb(appUserId)))
                        .build()).getBody();
    }
    @GetMapping("/web/screen-total-sf/get-chart/{appUserId}")
    public ApiResponse<List<MonthlyStatisticScreenTotalSFResponseDTO>> getPoint12MonthWebSF(
            @PathVariable Integer appUserId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MonthlyStatisticScreenTotalSFResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(FormatDTOUtils.formatWebScreenTotalSF(monthlyQuestionService.getPoint12MonthWeb(appUserId)))
                        .build()).getBody();
    }

    //Lấy chart chi tiết của 2 tháng gần với tháng nhập vào
    //Screen 2 month sat
    @GetMapping("/mobile/screen-2-month-sat/get-detail/{monthNumber}")
    public ApiResponse<List<MobileScreen2MonthChartSATResponseDTO>> getPoint2MonthMobileSAT(@PathVariable Integer monthNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MobileScreen2MonthChartSATResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(FormatDTOUtils.formatMobileScreen2MonthSAT(monthlyQuestionService.getPoint2MonthMobile(monthNumber)))
                        .build()).getBody();

    }
    //Screen 2 month sf
    @GetMapping("/mobile/screen-2-month-sf/get-detail/{monthNumber}")
    public ApiResponse<List<MobileScreen2MonthChartSFResponseDTO>> getPoint2MonthMobileSF(@PathVariable Integer monthNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MobileScreen2MonthChartSFResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(FormatDTOUtils.formatMobileScreen2MonthSF(monthlyQuestionService.getPoint2MonthMobile(monthNumber)))
                        .build()).getBody();

    }
    //Screen 2 month sat
    @GetMapping("/web/screen-2-month-sat/{appUserId}/{monthNumber}")
    public ApiResponse<List<MobileScreen2MonthChartSATResponseDTO>> getPoint2MonthWebSAT(
            @PathVariable Integer appUserId,
            @PathVariable Integer monthNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MobileScreen2MonthChartSATResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(FormatDTOUtils.formatMobileScreen2MonthSAT(monthlyQuestionService.getPoint2MonthWeb(appUserId,monthNumber)))
                        .build()).getBody();

    }
    //Screen 2 month sf
    @GetMapping("/web/screen-2-month-sf/{appUserId}/{monthNumber}")
    public ApiResponse<List<MobileScreen2MonthChartSFResponseDTO>> getPoint2MonthWebSF(
            @PathVariable Integer appUserId,
            @PathVariable Integer monthNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MobileScreen2MonthChartSFResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(FormatDTOUtils.formatMobileScreen2MonthSF(monthlyQuestionService.getPoint2MonthWeb(appUserId,monthNumber)))
                        .build()).getBody();

    }


}