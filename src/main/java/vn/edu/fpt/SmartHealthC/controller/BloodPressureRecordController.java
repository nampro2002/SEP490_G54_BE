package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/blood-pressure")
public class BloodPressureRecordController {

    @Autowired
    private BloodPressureRecordService bloodPressureRecordService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<BloodPressureRecord> createBloodPressureRecord(@RequestBody @Valid BloodPressureRecordDTO bloodPressureRecordDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(bloodPressureRecordService.createBloodPressureRecord(bloodPressureRecordDTO))
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/chart")
    public ApiResponse<BloodPressureResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BloodPressureResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.getDataChart())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<BloodPressureResponseDTO>> getAllBloodPressureRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BloodPressureResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.getListBloodPressureRecordsByUser(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateBloodPressureRecord(@PathVariable Integer id, @RequestBody @Valid BloodPressureRecordDTO bloodPressureRecordDTO) {
        bloodPressureRecordService.updateBloodPressureRecord(id, bloodPressureRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }
    //Test only
    @GetMapping("/{id}")
    public ApiResponse<BloodPressureRecord> getBloodPressureRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.getBloodPressureRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<BloodPressureRecord> deleteBloodPressureRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<BloodPressureRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(bloodPressureRecordService.deleteBloodPressureRecord(id))
                        .build()).getBody();
    }
}