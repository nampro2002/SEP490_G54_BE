package vn.edu.fpt.SmartHealthC.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ApiResponse<?> loginStaff(
            @Valid @RequestBody LoginDto loginDto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.loginStaff(loginDto))
                        .build()).getBody();
    }

    @PostMapping("/staff")
    public ApiResponse<?> createStaff(@RequestBody @Valid WebUserRequestDTO account) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(accountService.createStaff(account))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(accountService.getAccountById(id).get())
                        .build()).getBody();
    }

    @GetMapping("activate/{id}")
    public ResponseEntity<?> activateAccount(@PathVariable Integer appUserId) {
        if (accountService.activateAccount(appUserId)) {
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(ApiResponse.<Account>builder()
//                            .code(HttpStatus.CREATED.value())
//                            .result(accountService.getAccountById(id).get())
//                            .build()).getBody();
            return ResponseEntity.ok().build();
        }
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMessage("Activation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/email/{email}")
    public ApiResponse<?> getAccountByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(accountService.getAccountByEmail(email).get())
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<Account>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<List<Account>>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(accountService.getAllAccounts())
                        .build()).getBody();
    }

 // active / changepass
    @PutMapping
    public ApiResponse<Account> updateAccount( @RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(accountService.updateAccount(account))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Account> deleteAccount(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(accountService.deleteAccount(id))
                        .build()).getBody();
    }
}
