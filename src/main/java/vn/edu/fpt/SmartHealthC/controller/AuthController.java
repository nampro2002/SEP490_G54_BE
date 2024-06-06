package vn.edu.fpt.SmartHealthC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.exception.BadRequestException;
import vn.edu.fpt.SmartHealthC.exception.DataNotFoundException;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;

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

    @PostMapping("/register")
    public ApiResponse<?> register(
            @RequestBody RegisterDto request) {
        return ApiResponse.<AuthenticationResponseDto>builder()
                .result(authService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponseDto> login(
            @RequestBody LoginDto request) {
        return ApiResponse.<AuthenticationResponseDto>builder()
                .result(authService.login(request))
                .build();
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccountList(){
        return ResponseEntity.ok(accountRepository.findAll());
    }
}
