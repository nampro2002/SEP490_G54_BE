package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;

import java.util.List;

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

    @PostMapping("/register")
    public ApiResponse<?> register(
            @RequestBody @Valid RegisterDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .isSuccess(true)
                        .code(HttpStatus.CREATED)
                        .result(authService.register(request))
                        .build()).getBody();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponseDto> login(
            @RequestBody @Valid LoginDto request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .isSuccess(true)
                        .code(HttpStatus.OK)
                        .result(authService.login(request))
                        .build()).getBody();
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccountList(){
        return ResponseEntity.ok(accountRepository.findAll());
    }
}