package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ChangePasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;

import java.text.ParseException;

@RestController
@RequestMapping("/api/forget-password")
public class ForgetPasswordCodeController {

    @Autowired
    private ForgetPasswordCodeService forgetPasswordCodeService;

    @GetMapping("/email/{email}")
    public ApiResponse<String> sendEmail(@PathVariable String email) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.sendEmailCode(email))
                        .build()).getBody();
    }
    @GetMapping("/web/email/{email}")
    public ApiResponse<String> sendPasswordEmail(@PathVariable String email) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.sendPasswordEmail(email))
                        .build()).getBody();
    }

    @PostMapping("/email/verify")
    public ApiResponse<Boolean> checkEmailCodeInvalid(@RequestBody @Valid ForgetPasswordCodeDTO forgetPasswordCodeDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.checkEmailCodeInvalid(forgetPasswordCodeDTO))
                        .build()).getBody();
    }

    @PostMapping("/email/change-password")
    public ApiResponse<Boolean> verifyCodeAndChangePassword(@RequestBody @Valid ChangePasswordCodeDTO changePasswordCodeDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.verifyAndChangePassword(changePasswordCodeDTO))
                        .build()).getBody();
    }

}