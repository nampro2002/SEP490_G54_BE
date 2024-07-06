package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyMoblieChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyReviewResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.WeekReview;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/weekly-reviews")
public class WeeklyReviewController {

    @Autowired
    private WeeklyReviewService weeklyReviewService;

    @GetMapping("/web/week-date/{appUserId}")
    public ApiResponse<WeekReview> returnWeekDate(@PathVariable Integer appUserId) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeekReview>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getWeek(appUserId))
                        .build()).getBody();
    }
    @GetMapping("/web/week-starts/{appUserId}")
    public ApiResponse<List<Date>> returnListWeekStart(@PathVariable Integer appUserId) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Date>>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getListWeekStart(appUserId))
                        .build()).getBody();
    }
    @GetMapping("/mobile/week-starts/{appUserId}")
    public ApiResponse<List<Date>> returnMobileListWeekStart(@PathVariable Integer appUserId) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Date>>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getListWeekStart(appUserId))
                        .build()).getBody();
    }

    @GetMapping("/web/review/{weekstart}")
    public ApiResponse<WeekReview> returnReviewForWeekDate(@PathVariable String weekstart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeekReview>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getWebDataReviewForWeek(weekstart))
                        .build()).getBody();
    }
    @GetMapping("/mobile/review/{weekstart}")
    public ApiResponse<WeeklyReviewResponseDTO> returnMobileReviewForWeekDate(@PathVariable String weekstart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeeklyReviewResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getMobileDataReviewForWeek(weekstart))
                        .build()).getBody();
    }
    @GetMapping("/mobile/chart/review")
    public ApiResponse<WeeklyMoblieChartResponseDTO> returnMobileChartReview() throws ParseException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeeklyMoblieChartResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getMobileChartReviewForWeek())
                        .build()).getBody();
    }


}