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
import vn.edu.fpt.SmartHealthC.exception.BadRequestException;
import vn.edu.fpt.SmartHealthC.exception.DataNotFoundException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
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
            @RequestBody @Valid RegisterDto request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(ErrorCode.USER_CREATED.getMessage())
                        .build()).getBody();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponseDto> login(
            @RequestBody @Valid LoginDto request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .result(authService.login(request))
                        .build()).getBody();
    }

//    @GetMapping("/accounts")
//    public ResponseEntity<List<Account>> getAccountList(){
//        return ResponseEntity.ok(accountRepository.findAll());
//    }
}
