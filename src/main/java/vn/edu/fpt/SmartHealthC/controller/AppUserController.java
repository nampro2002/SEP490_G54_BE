package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AppUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/app-user")
@RequiredArgsConstructor
public class AppUserController {

    @Autowired
    private final AppUserService appUserService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    //getListAppUser with paging and search
    @GetMapping("/web")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getListAppUser (@RequestParam(defaultValue = "1") Integer pageNo,
                                          @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.getListAppUser(pageNo -1, search))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/detail/{id}")
    public ApiResponse<AppUserDetailResponseDTO> getAppUserDetailById (@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AppUserDetailResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.getAppUserDetailById(id))
                        .build()).getBody();
    }

    @GetMapping("/mobile/get-height-weight")
    public ApiResponse<AppUserNameHeightWeightResponseDTO> getAppUserNameHeightWeight () {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AppUserNameHeightWeightResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.getAppUserNameHeightWeight())
                        .build()).getBody();
    }
//    @GetMapping("mobile/detail")
//    public ApiResponse<AppUserDetailResponseDTO> getAppUserDetailMobile () {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<AppUserDetailResponseDTO>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(appUserService.getAppUserDetailMobile())
//                        .build()).getBody();
//    }
    @PreAuthorize("hasAuthority('MEDICAL_SPECIALIST')")
    @PutMapping("/{id}")
    public ApiResponse<AppUserDetailResponseDTO> updateAppUser (@PathVariable Integer id, @RequestBody @Valid AppUserRequestDTO appUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AppUserDetailResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.updateAppUser(id, appUserRequestDTO))
                        .build()).getBody();
    }


}
