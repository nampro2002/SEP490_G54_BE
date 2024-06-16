package vn.edu.fpt.SmartHealthC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;
import vn.edu.fpt.SmartHealthC.serivce.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/web/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final QuestionService questionService;
    private final MedicalAppointmentService medicalAppointmentService;
    private final AccountService accountService;


    @GetMapping("/admin-question")
    public ApiResponse<List<QuestionResponseDTO>> getQuestionResponseListAdmin() {
        List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(responseDTOList)
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

    @GetMapping("/register-request")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getUserPendingList(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getPendingAccount(pageNo - 1))
                        .build()).getBody();
    }
    @GetMapping("/assign-pending")
    public ApiResponse<ResponsePaging<List<AppUserResponseDTO>>> getUserPendingAssignList(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<AppUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(accountService.getUserPendingAssign(pageNo - 1))
                        .build()).getBody();
    }

    @GetMapping("/ms-question")
    public ApiResponse<List<QuestionResponseDTO>> getQuestionResponseListMs() {
        List<QuestionResponseDTO> responseDTOList = questionService.getAllPendingQuestionsByType(TypeUserQuestion.ASSIGN_MS);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(responseDTOList)
                        .build()).getBody();
    }

    @GetMapping("/medical-appointment/diagnosis")
    public ApiResponse<ResponsePaging<List<MedicalAppointmentResponseDTO>>> getAllMedicalAppointmentsDiagnosisPending(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MedicalAppointmentResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getAllMedicalAppointmentsPending(pageNo - 1, TypeMedicalAppointment.DIAGNOSIS))
                        .build()).getBody();
    }

    @GetMapping("/medical-appointment/checkup")
    public ApiResponse<ResponsePaging<List<MedicalAppointmentResponseDTO>>> getAllMedicalAppointmentsCheckupPending(@RequestParam(defaultValue = "1") Integer pageNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MedicalAppointmentResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentService.getAllMedicalAppointmentsPending(pageNo - 1, TypeMedicalAppointment.MEDICAL_CHECKUP))
                        .build()).getBody();
    }


//    @GetMapping("/{id}")
//    public ApiResponse<DashboardResponseDTO> dashboardX(@PathVariable Integer id) {
//        List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestionsByType(TypeUserQuestion.ASSIGN_MS);
//        List<MedicalAppointmentResponseDTO> medicalAppointmentResponseDTOList = medicalAppointmentService.getAllMedicalAppointmentsPending(id);
//        DashboardResponseDTO dashboardResponseDTOS = DashboardResponseDTO.builder()
//                .questionResponseDTOS(responseDTOList)
//                .medicalAppointmentResponseDTOList(medicalAppointmentResponseDTOList)
//                .build();
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<DashboardResponseDTO>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(dashboardResponseDTOS)
//                        .build()).getBody();
//    }
//@GetMapping
//public ApiResponse<DashboardResponseDTO> dashboard(@RequestParam(defaultValue = "0") Integer pageNo) {
//    List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN);
//    List<AppUserResponseDTO> appUserResponseDTOList = accountService.getPendingAccount(pageNo);
//    List<AvailableMSResponseDTO> availableMSResponseDTOList = accountService.getAvailableMS();
//    DashboardResponseDTO dashboardResponseDTOS = DashboardResponseDTO.builder()
//            .questionResponseDTOS(responseDTOList)
//            .availableMSResponseDTOList(availableMSResponseDTOList)
//            .appUserResponseDTOList(appUserResponseDTOList)
//            .build();
//    return ResponseEntity.status(HttpStatus.OK)
//            .body(ApiResponse.<DashboardResponseDTO>builder()
//                    .code(HttpStatus.OK.value())
//                    .result(dashboardResponseDTOS)
//                    .build()).getBody();
//}
}
