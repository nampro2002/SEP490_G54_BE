package vn.edu.fpt.SmartHealthC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/web/patient")
@RequiredArgsConstructor
public class PatientController {
    private final AccountService accountService;

    @GetMapping("/register-request")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getUserPendingList(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getPendingAccount(pageNo - 1, TypeAccount.USER))
                        .build()).getBody();
    }
    @GetMapping("/assign-request")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getAssignPendingList(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getUserPendingAssign(pageNo - 1))
                        .build()).getBody();
    }
}
