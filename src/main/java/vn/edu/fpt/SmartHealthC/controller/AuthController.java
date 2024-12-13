package vn.edu.fpt.SmartHealthC.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.RefreshTokenResponseDto;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.exception.BadRequestException;
import vn.edu.fpt.SmartHealthC.exception.DataNotFoundException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AccountRepository accountRepository;
    @GetMapping("/demo")
    public String demoAccess() {
        return "Connection Established";
    }

    @PostMapping("/mobile/register")
    public ApiResponse<?> register(
            @RequestBody @Valid RegisterDto request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(ErrorCode.USER_CREATED.getMessage())
                        .build()).getBody();
    }
    @PostMapping("/register-doctor")
    public ApiResponse<?> registerDoctor(
            @RequestBody @Valid DoctorRegisterDto request) {
        authService.registerDoctor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(ErrorCode.DOCTOR_CREATED.getMessage())
                        .build()).getBody();
    }
    @GetMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .message(ErrorCode.LOGOUT_SUCCESS.getMessage())
                        .build()).getBody();
    }
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponseDto> login(
            @RequestBody @Valid LoginDto request) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .result(authService.login(request))
                        .build()).getBody();
    }
//
    @GetMapping("/verify-mail/{email}")
    public ApiResponse<String> sendEmailCode(
            @PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<String>builder()
                        .code(HttpStatus.OK.value())
                        .result(authService.sendEmailCode(email))
                        .build()).getBody();
    }
    @GetMapping("/check-register-email/{email}/{code}")
    public ApiResponse<Boolean> checkRegisterEmail(
            @PathVariable String email,
            @PathVariable String code) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(authService.checkRegisterEmail(email,code))
                        .build()).getBody();
    }

    @GetMapping("/check-refresh-token/{token}")
    public ApiResponse<Boolean> checkRefreshToken(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable String token) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(authService.checkRefreshToken(token,request,response))
                        .build()).getBody();
    }

    @GetMapping("/refresh-token/{token}")
    public ApiResponse<RefreshTokenResponseDto> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable String token) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RefreshTokenResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .result(authService.refreshToken(token,request,response))
                        .build()).getBody();
    }

//    @GetMapping("/accounts")
//    public ResponseEntity<List<Account>> getAccountList(){
//        return ResponseEntity.ok(accountRepository.findAll());
//    }
}
