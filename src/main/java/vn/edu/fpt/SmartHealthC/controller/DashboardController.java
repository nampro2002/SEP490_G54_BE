package vn.edu.fpt.SmartHealthC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;
import vn.edu.fpt.SmartHealthC.serivce.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final QuestionService questionService;
    private final MedicalAppointmentService medicalAppointmentService;
    private final AccountService accountService;

    @GetMapping
    public ApiResponse<DashboardResponseDTO> dashboard() {
        List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN);
        List<AppUserResponseDTO> appUserResponseDTOList = accountService.getPendingAccount();
        List<AvailableMSResponseDTO> availableMSResponseDTOList = accountService.getAvailableMS();
        DashboardResponseDTO dashboardResponseDTOS = DashboardResponseDTO.builder()
                .questionResponseDTOS(responseDTOList)
                .availableMSResponseDTOList(availableMSResponseDTOList)
                .appUserResponseDTOList(appUserResponseDTOList)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DashboardResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(dashboardResponseDTOS)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<DashboardResponseDTO> dashboard(@PathVariable Integer id) {
        List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestionsByType(TypeUserQuestion.ASSIGN_MS);
        List<MedicalAppointmentResponseDTO> medicalAppointmentResponseDTOList = medicalAppointmentService.getAllMedicalAppointmentsPending(id);
        DashboardResponseDTO dashboardResponseDTOS = DashboardResponseDTO.builder()
                .questionResponseDTOS(responseDTOList)
                .medicalAppointmentResponseDTOList(medicalAppointmentResponseDTOList)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DashboardResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(dashboardResponseDTOS)
                        .build()).getBody();
    }
}
