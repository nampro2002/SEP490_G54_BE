package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.WeekCheckPlanResponseDTO;
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

        @PostMapping("/create-weekly-review/{appUserId}/{weekstart}")
    public ApiResponse<Void> createWeeklyReview(@PathVariable Integer appUserId,@PathVariable String weekstart) throws ParseException {
        weeklyReviewService.saveDataReviewForWeek(appUserId,weekstart);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }

    //Lấy data tuần gần nhất
    @GetMapping("/web/week-date/{appUserId}")
    public ApiResponse<WeekReview> returnWeekDate(@PathVariable Integer appUserId) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeekReview>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getDataOfNearestWeek(appUserId))
                        .build()).getBody();
    }
    //Lấy danh sách weekStart
    @GetMapping("/web/week-starts/{appUserId}")
    public ApiResponse<List<Date>> returnListWeekStart(@PathVariable Integer appUserId) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Date>>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.getListWeekStart(appUserId))
                        .build()).getBody();
    }
    //Lấy list 5 tuần gần nhất
    @GetMapping("/mobile/week-starts")
    public ApiResponse<List<Date>> returnMobileListWeekStart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Date>>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.get5NearestWeekStart())
                        .build()).getBody();
    }

//    @GetMapping("/mobile/all-list")
//    public ApiResponse<List<Date>> allList() throws ParseException {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<Date>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(weeklyReviewService.allList())
//                        .build()).getBody();
//    }

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
    @GetMapping("/mobile/check/week-plan/{weekStart}")
    public ApiResponse<WeekCheckPlanResponseDTO> checkWeeklyPlanExist(
            @PathVariable String weekStart
    ) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeekCheckPlanResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(weeklyReviewService.checkWeeklyPlanExist (weekStart))
                        .build()).getBody();
    }


}