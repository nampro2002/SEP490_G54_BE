package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.serivce.UserWeek1InformationService;
import vn.edu.fpt.SmartHealthC.serivce.WeightRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/user-week1-information")
public class UserWeek1InformationController {

    @Autowired
    private UserWeek1InformationService userWeek1InformationService;

    @GetMapping("/lesson1/{id}")
    public ApiResponse<Lesson1DTO> getLesson1(@PathVariable Integer id) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson1DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson1(id))
                        .build()).getBody();
    }
    @PostMapping("/lesson1/{id}")
    public ApiResponse<Void> setLesson1(
            @PathVariable Integer id,
            @RequestBody @Valid Lesson1DTO lesson1DTO) throws ParseException {
        userWeek1InformationService.setLesson1(id,lesson1DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson2/{id}")
    public ApiResponse<Lesson2DTO> getLesson2(@PathVariable Integer id) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson2DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson2(id))
                        .build()).getBody();
    }
    @PostMapping("/lesson2/{id}")
    public ApiResponse<Void> setLesson2(
            @PathVariable Integer id,
            @RequestBody @Valid Lesson2DTO lesson2DTO) throws ParseException {
        userWeek1InformationService.setLesson2(id,lesson2DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson3/{id}")
    public ApiResponse<Lesson3DTO> getLesson3(@PathVariable Integer id) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson3DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson3(id))
                        .build()).getBody();
    }
    @PostMapping("/lesson3/{id}")
    public ApiResponse<Void> setLesson2(
            @PathVariable Integer id,
            @RequestBody @Valid Lesson3DTO lesson3DTO) throws ParseException {
        userWeek1InformationService.setLesson3(id,lesson3DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson4/{id}")
    public ApiResponse<Lesson4DTO> getLesson4(@PathVariable Integer id) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson4DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson4(id))
                        .build()).getBody();
    }
    @PostMapping("/lesson4/{id}")
    public ApiResponse<Void> setLesson4(
            @PathVariable Integer id,
            @RequestBody @Valid Lesson4DTO lesson4DTO) throws ParseException {
        userWeek1InformationService.setLesson4(id,lesson4DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson5/{id}")
    public ApiResponse<Lesson5DTO> getLesson5(@PathVariable Integer id) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson5DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson5(id))
                        .build()).getBody();
    }
    @PostMapping("/lesson5/{id}")
    public ApiResponse<Void> setLesson5(
            @PathVariable Integer id,
            @RequestBody @Valid Lesson5DTO lesson5DTO) throws ParseException {
        userWeek1InformationService.setLesson5(id,lesson5DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson6/{id}")
    public ApiResponse<Lesson6DTO> getLesson6(@PathVariable Integer id) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson6DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson6(id))
                        .build()).getBody();
    }
    @PostMapping("/lesson6/{id}")
    public ApiResponse<Void> setLesson6(
            @PathVariable Integer id,
            @RequestBody @Valid Lesson6DTO lesson6DTO) throws ParseException {
        userWeek1InformationService.setLesson6(id,lesson6DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson7/{id}")
    public ApiResponse<Lesson7DTO> getLesson7(@PathVariable Integer id) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson7DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson7(id))
                        .build()).getBody();
    }
    @PostMapping("/lesson7/{id}")
    public ApiResponse<Void> setLesson7(
            @PathVariable Integer id,
            @RequestBody @Valid Lesson7DTO lesson7DTO) throws ParseException {
        userWeek1InformationService.setLesson7(id,lesson7DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

}