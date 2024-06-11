package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forget-password")
public class ForgetPasswordCodeController {

    @Autowired
    private ForgetPasswordCodeService forgetPasswordCodeService;

    @PostMapping
    public ApiResponse<ForgetPasswordCode> createForgetPasswordCode(@RequestBody ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ForgetPasswordCode>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(forgetPasswordCodeService.createForgetPasswordCode(forgetPasswordCodeDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<ForgetPasswordCode> getForgetPasswordCodeById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ForgetPasswordCode>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.getForgetPasswordCodeById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<ForgetPasswordCode>> getAllForgetPasswordCodes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ForgetPasswordCode>>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.getAllForgetPasswordCodes())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<ForgetPasswordCode> updateForgetPasswordCode(@PathVariable Integer id, @RequestBody ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ForgetPasswordCode>builder()
                        .code(HttpStatus.OK.value())
                        .result( forgetPasswordCodeService.updateForgetPasswordCode(id, forgetPasswordCodeDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<ForgetPasswordCode> deleteForgetPasswordCode(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ForgetPasswordCode>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.deleteForgetPasswordCode(id))
                        .build()).getBody();
    }
}