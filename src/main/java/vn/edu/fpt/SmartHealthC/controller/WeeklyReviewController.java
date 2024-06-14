package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeeklyReviewResponseDTO;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/weekly-reviews")
public class WeeklyReviewController {

    @Autowired
    private WeeklyReviewService weeklyReviewService;

    @GetMapping("/{appUserId}")
    public ApiResponse<WeeklyReviewResponseDTO> returnWeekDate(@PathVariable Integer appUserId) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeeklyReviewResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getWeekDate(appUserId))
                        .build()).getBody();
    }
    @GetMapping("/week-starts/{appUserId}")
    public ApiResponse<List<Date>> returnListWeekStart(@PathVariable Integer appUserId) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Date>>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getListWeekStart(appUserId))
                        .build()).getBody();
    }

    @GetMapping("/{appUserId}/{weekstart}")
    public ApiResponse<WeeklyReviewResponseDTO> returnReviewForWeekDate(@PathVariable Integer appUserId,
                                                     @PathVariable String weekstart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeeklyReviewResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getDataReviewForWeekDate(appUserId,weekstart))
                        .build()).getBody();
    }



}