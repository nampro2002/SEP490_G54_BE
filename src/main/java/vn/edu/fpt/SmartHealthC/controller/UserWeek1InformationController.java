package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.serivce.UserWeek1InformationService;

import java.text.ParseException;

@RestController
@RequestMapping("/api/user-week1-information")
public class UserWeek1InformationController {

    @Autowired
    private UserWeek1InformationService userWeek1InformationService;

    @GetMapping("/unlocked-lessons")
    public  ApiResponse<Integer> getUnlockedLessons() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Integer>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getUnlockedLessons())
                        .build()).getBody();
    }

    @GetMapping("/lesson1")
    public ApiResponse<Lesson1DTO> getLesson1() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson1DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson1())
                        .build()).getBody();
    }
    @PutMapping("/lesson1")
    public ApiResponse<Void> setLesson1(
          
            @RequestBody @Valid Lesson1DTO lesson1DTO) throws ParseException {
        userWeek1InformationService.setLesson1(lesson1DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson2")
    public ApiResponse<Lesson2DTO> getLesson2() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson2DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson2())
                        .build()).getBody();
    }
    @PutMapping("/lesson2")
    public ApiResponse<Void> setLesson2(
            @RequestBody @Valid Lesson2DTO lesson2DTO) throws ParseException {
        userWeek1InformationService.setLesson2(lesson2DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson3")
    public ApiResponse<Lesson3DTO> getLesson3() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson3DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson3())
                        .build()).getBody();
    }
    @PutMapping("/lesson3")
    public ApiResponse<Void> setLesson2(
         
            @RequestBody @Valid Lesson3DTO lesson3DTO) throws ParseException {
        userWeek1InformationService.setLesson3(lesson3DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson4")
    public ApiResponse<Lesson4DTO> getLesson4() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson4DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson4())
                        .build()).getBody();
    }
    @PutMapping("/lesson4")
    public ApiResponse<Void> setLesson4(
            
            @RequestBody @Valid Lesson4DTO lesson4DTO) throws ParseException {
        userWeek1InformationService.setLesson4(lesson4DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson5")
    public ApiResponse<Lesson5DTO> getLesson5() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson5DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson5())
                        .build()).getBody();
    }
    @PutMapping("/lesson5")
    public ApiResponse<Void> setLesson5(
            @RequestBody @Valid Lesson5DTO lesson5DTO) throws ParseException {
        userWeek1InformationService.setLesson5(lesson5DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson6")
    public ApiResponse<Lesson6DTO> getLesson6() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson6DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson6())
                        .build()).getBody();
    }
    @PutMapping("/lesson6")
    public ApiResponse<Void> setLesson6(
            @RequestBody @Valid Lesson6DTO lesson6DTO) throws ParseException {
        userWeek1InformationService.setLesson6(lesson6DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

    @GetMapping("/lesson7")
    public ApiResponse<Lesson7DTO> getLesson7() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Lesson7DTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(userWeek1InformationService.getLesson7())
                        .build()).getBody();
    }
    @PutMapping("/lesson7")
    public ApiResponse<Void> setLesson7(
            @RequestBody @Valid Lesson7DTO lesson7DTO) throws ParseException {
        userWeek1InformationService.setLesson7(lesson7DTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(null)
                        .build()).getBody();
    }

}