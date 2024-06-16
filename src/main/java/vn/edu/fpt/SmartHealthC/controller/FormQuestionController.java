package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/form-question")
public class FormQuestionController {
    @Autowired
    private FormQuestionService formQuestionService;

    @PostMapping
    public ApiResponse<FormQuestionResponseDTO> createFormQuestion(@RequestBody @Valid FormQuestionRequestDTO formQuestion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(formQuestionService.createFormQuestion(formQuestion))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getFormQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.getFormQuestionById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<ResponsePaging<List<FormQuestionResponseDTO>>> getAllFormQuestions(@RequestParam(defaultValue = "1") Integer pageNo,  @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<FormQuestionResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.getAllFormQuestions(pageNo-1, search))
                        .build()).getBody();
    }
    @GetMapping("/mobile")
    public ApiResponse<List<FormQuestionResponseDTO>> getAllFormQuestionsMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FormQuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.getAllFormQuestionsMobile())
                        .build()).getBody();
    }


    @PutMapping({"/{id}"})
    public ApiResponse<FormQuestionResponseDTO> updateFormQuestion(@PathVariable Integer id, @RequestBody @Valid FormQuestionRequestDTO formQuestion) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.updateFormQuestion(id, formQuestion))
                        .build()).getBody();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<FormQuestionResponseDTO> deleteFormQuestion(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result( formQuestionService.deleteFormQuestion(id))
                        .build()).getBody();
    }
}
