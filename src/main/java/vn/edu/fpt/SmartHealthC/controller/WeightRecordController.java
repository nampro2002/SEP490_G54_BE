package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.serivce.WeightRecordService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weight-records")
public class WeightRecordController {

    @Autowired
    private WeightRecordService weightRecordService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<WeightRecord> createWeightRecord(@RequestBody @Valid WeightRecordDTO weightRecordDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<WeightRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(weightRecordService.createWeightRecord(weightRecordDTO))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/chart")
    public ApiResponse<WeightResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeightResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.getDataChart())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<WeightResponseDTO>> getAllWeightRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<WeightResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.getWeightRecordList(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateWeightRecord(@PathVariable Integer id, @RequestBody @Valid WeightRecordDTO weightRecordDTO) {
        weightRecordService.updateWeightRecord(id, weightRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }
    //Test only
    @GetMapping("/{id}")
    public ApiResponse<WeightRecord> getWeightRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeightRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.getWeightRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<WeightRecord> deleteWeightRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WeightRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(weightRecordService.deleteWeightRecord(id))
                        .build()).getBody();
    }
}