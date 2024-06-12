package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;

@RestController
@RequestMapping("/api/forget-password")
public class ForgetPasswordCodeController {

    @Autowired
    private ForgetPasswordCodeService forgetPasswordCodeService;

    @PostMapping("/send-email")
    public ApiResponse<String> sendEmail(@RequestBody ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .result(forgetPasswordCodeService.sendEmailCode(forgetPasswordCodeDTO))
                        .build()).getBody();
    }

}