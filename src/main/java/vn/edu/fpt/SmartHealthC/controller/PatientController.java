package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/web/patient")
@RequiredArgsConstructor
public class PatientController {
    private final AccountService accountService;

    private final AppUserService appUserService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/register-request")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getUserPendingList(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getPendingAccount(pageNo - 1, TypeAccount.USER))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/assign-request")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getAssignPendingList(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getUserPendingAssign(pageNo - 1))
                        .build()).getBody();
    }
    @GetMapping("/availablems")
    public ApiResponse<List<AvailableMSResponseDTO>> getAvailableMSList() {
        List<AvailableMSResponseDTO> availableMSResponseDTOList = accountService.getAvailableMS();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<AvailableMSResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(availableMSResponseDTOList)
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/assign")
    public ApiResponse<AppUserAssignResponseDTO> assignPatientToDoctor (@RequestBody @Valid AssignRequestDTO assignRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AppUserAssignResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.assignPatientToDoctor(assignRequestDTO))
                        .build()).getBody();
    }
    @GetMapping("/detail/{id}")
    public ApiResponse<AppUserDetailResponseDTO> getAppUserDetailById (@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AppUserDetailResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.getAppUserDetailById(id))
                        .build()).getBody();
    }
}
