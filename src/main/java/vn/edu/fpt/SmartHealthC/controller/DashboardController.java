package vn.edu.fpt.SmartHealthC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("admin-question")
    public ApiResponse<List<QuestionResponseDTO>> getQuestionResponseListAdmin(){
        List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestionsByType(TypeUserQuestion.ASSIGN_ADMIN);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(responseDTOList)
                        .build()).getBody();
    }
    @GetMapping("/availablems")
    public ApiResponse<List<AvailableMSResponseDTO>> getAvailableMSList(){
        List<AvailableMSResponseDTO> availableMSResponseDTOList = accountService.getAvailableMS();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<AvailableMSResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(availableMSResponseDTOList)
                        .build()).getBody();
    }
    @GetMapping("/register-request")
    public ApiResponse<List<AppUserResponseDTO>> getUserPendingList(@RequestParam(defaultValue = "1") Integer pageNo){
        List<AppUserResponseDTO> appUserResponseDTOList = accountService.getPendingAccount(pageNo);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<AppUserResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(appUserResponseDTOList)
                        .build()).getBody();
    }

    @GetMapping("ms-question")
    public ApiResponse<List<QuestionResponseDTO>> getQuestionResponseListMs(){
        List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestionsByType(TypeUserQuestion.ASSIGN_MS);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<QuestionResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(responseDTOList)
                        .build()).getBody();
    }
    @GetMapping("/medical-appointment")
    public ApiResponse<List<MedicalAppointmentResponseDTO>> getQuestionResponseList(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer pageNo){
        List<MedicalAppointmentResponseDTO> medicalAppointmentResponseDTOList = medicalAppointmentService.getAllMedicalAppointmentsPending(id, pageNo);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicalAppointmentResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicalAppointmentResponseDTOList)
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