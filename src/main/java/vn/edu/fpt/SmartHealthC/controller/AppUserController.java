package vn.edu.fpt.SmartHealthC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AppUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserDetailResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/app-user")
@RequiredArgsConstructor
public class AppUserController {

    @Autowired
    private final AppUserService appUserService;

    @PutMapping
    public ApiResponse<?> assignPatientToDoctor (@RequestBody AssignRequestDTO assignRequestDTO) {
        appUserService.assignPatientToDoctor(assignRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Account>builder()
                        .code(HttpStatus.OK.value())
                        .message("Assign success")
                        .build()).getBody();
    }

    //getListAppUser with paging and search
    @GetMapping("/{id}")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getListAppUser (@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer pageNo,
                                          @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.getListAppUser(pageNo -1, search, id))
                        .build()).getBody();
    }
    //getAppUserById
    @GetMapping("user/{id}")
    public ApiResponse<AppUserDetailResponseDTO> getAppUserDetailById (@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AppUserDetailResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.getAppUserDetailById(id))
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<AppUserDetailResponseDTO> updateAppUser (@PathVariable Integer id, @RequestBody AppUserRequestDTO appUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AppUserDetailResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserService.updateAppUser(id, appUserRequestDTO))
                        .build()).getBody();
    }


}
