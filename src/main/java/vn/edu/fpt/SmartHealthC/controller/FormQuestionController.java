package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/form-question")
public class FormQuestionController {
    @Autowired
    private FormQuestionService formQuestionService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<FormQuestionResponseDTO> createFormQuestion(@RequestBody @Valid FormQuestionRequestDTO formQuestion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(formQuestionService.createFormQuestion(formQuestion))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<?> getFormQuestionById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.getFormQuestionById(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/web/others")
    public ApiResponse<ResponsePaging<List<FormQuestionResponseDTO>>> getAllFormQuestions(@RequestParam(defaultValue = "1") Integer pageNo,  @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<FormQuestionResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.getAllFormQuestions(pageNo-1, search))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile")
    public ApiResponse<List<FormQuestionResponseDTO>> getAllFormQuestionsMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FormQuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.getAllFormQuestionsMobile())
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping({"/{id}"})
    public ApiResponse<FormQuestionResponseDTO> updateFormQuestion(@PathVariable Integer id, @RequestBody @Valid FormQuestionRequestDTO formQuestion) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(formQuestionService.updateFormQuestion(id, formQuestion))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<FormQuestionResponseDTO> deleteFormQuestion(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FormQuestionResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result( formQuestionService.deleteFormQuestion(id))
                        .build()).getBody();
    }
}
