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
                        .isSuccess(true)
                        .code(HttpStatus.OK)
                        .result(accountService.loginStaff(loginDto))
                        .build()).getBody();
    }

    @PostMapping
    public ApiResponse<?> createStaff(@RequestBody @Valid WebUserRequestDTO account) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AuthenticationResponseDto>builder()
                        .isSuccess(true)
                        .code(HttpStatus.CREATED)
                        .result(accountService.createStaff(account))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable String email) {
        Optional<Account> account = accountService.getAccountByEmail(email);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Integer id, @RequestBody Account account) {
        account.setId(id);
        Account updatedAccount = accountService.updateAccount(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
