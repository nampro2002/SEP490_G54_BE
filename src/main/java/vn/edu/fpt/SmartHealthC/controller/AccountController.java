package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UpdatePasswordRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

//    @PostMapping("/login")
//    public ApiResponse<?> loginStaff(
//            @Valid @RequestBody LoginDto loginDto,
//            HttpServletRequest request
//    ) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<AuthenticationResponseDto>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(accountService.loginStaff(loginDto))
//                        .build()).getBody();
//    }

    @PostMapping("/staff")
    public ApiResponse<?> createStaff(@RequestBody @Valid WebUserRequestDTO account) {
        accountService.createStaff(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(ErrorCode.STAFF_CREATED.getMessage())
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getAccountById(id))
                        .build()).getBody();
    }

    @GetMapping("activate/{id}")
    public ApiResponse<?> activateAccount(@PathVariable Integer appUserId) {
        if (accountService.activateAccount(appUserId)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.CREATED.value())
                            .message("Activation successful")
                            .build()).getBody();
        }
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMessage("Activation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse).getBody();
    }

    @GetMapping("/email/{email}")
    public ApiResponse<?> getAccountByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getAccountByEmail(email))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<Account>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<Account>>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getAllAccounts())
                        .build()).getBody();
    }

    // active / changepass
    @PutMapping("change-pass/{id}")
    public ApiResponse<Account> changePassword(@PathVariable Integer id, @RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.changePassword(id, updatePasswordRequestDTO))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Account> deleteAccount(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.deleteAccount(id))
                        .build()).getBody();
    }
}
