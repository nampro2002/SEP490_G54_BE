package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/diet-records")
public class DietRecordController {

    @Autowired
    private DietRecordService dietRecordService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<Void> createDietRecord(@RequestBody @Valid DietRecordCreateDTO dietRecordDTO) throws ParseException {
       dietRecordService.createDietRecord(dietRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/chart")
    public ApiResponse<DietResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getDataChart())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/dish-plan/{weekStart}")
    public ApiResponse<Integer> getDishPlan(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Integer>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getDishPlan(weekStart))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<DietRecordListResDTO>> getAllDietRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DietRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getAllDietRecords(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("")
    public ApiResponse<Void> updateDietRecord(@RequestBody @Valid DietRecordUpdateDTO dietRecordDTO) throws ParseException {
        dietRecordService.updateDietRecord(dietRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }
    //Test only
    @GetMapping("/{id}")
    public ApiResponse<DietRecord> getDietRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getDietRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<DietRecord> deleteDietRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.deleteDietRecord(id))
                        .build()).getBody();
    }
}