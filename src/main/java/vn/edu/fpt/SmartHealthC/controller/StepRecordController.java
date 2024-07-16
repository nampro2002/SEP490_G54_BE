package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateContinuousDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CurrentStepRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicineResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepCount;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/step-records")
public class StepRecordController {

    @Autowired
    private StepRecordService stepRecordService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<Void> createStepRecord(@RequestBody @Valid StepRecordCreateDTO stepRecordDTO) throws ParseException {
       stepRecordService.createStepRecord(stepRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<StepRecordResListDTO>> getAllStepRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<StepRecordResListDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.getAllStepRecords(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/chart")
    public ApiResponse<StepResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.getDataChart())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("")
    public ApiResponse<Void> updateStepRecord( @RequestBody @Valid StepRecordUpdateDTO stepRecordDTO) throws ParseException {
        stepRecordService.updateStepRecord(stepRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }

    @PutMapping("/update-cont")
    public ApiResponse<Void> updateStepRecordNEW(@RequestBody @Valid StepRecordUpdateContinuousDTO stepRecordDTO) throws ParseException {
        stepRecordService.updateStepRecordNEW(stepRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/get-current-record")
    public ApiResponse<CurrentStepRecordResponseDTO> getCurrentRecord() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CurrentStepRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.getCurrentRecord())
                        .build()).getBody();
    }

//    @GetMapping("/getmonday")
//    public ApiResponse<Date> getmonday(@RequestBody @Valid StepRecordUpdateContinuousDTO stepRecordDTO) throws ParseException {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<Date>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(stepRecordService.getFirstDayOfWeek(stepRecordDTO))
//                        .build()).getBody();
//    }
    //Test only
    @GetMapping("/{id}")
    public ApiResponse<StepRecord> getStepRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(stepRecordService.getStepRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<StepRecord> deleteStepRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<StepRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result( stepRecordService.deleteStepRecord(id))
                        .build()).getBody();
    }
}