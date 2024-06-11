package vn.edu.fpt.SmartHealthC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

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
}
