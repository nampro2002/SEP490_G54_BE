package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/mental-records")
public class MentalRecordController {

    @Autowired
    private MentalRecordService mentalRecordService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<Void> createMentalRecord(@RequestBody @Valid MentalRecordCreateDTO mentalRecordDTO) throws ParseException {
        mentalRecordService.createMentalRecord(mentalRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/mental-rule/{weekStart}/{language}")
    public ApiResponse<List<MentalRule>> getMentalRuleByWeek(@PathVariable String weekStart,
    @PathVariable TypeLanguage language) throws ParseException {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRule>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getListMentalPerWeek(weekStart,language))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<MentalRecordListResDTO>> getAllMentalRecordsByAppUserId(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getAllMentalRecords(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/chart")
    public ApiResponse<MentalResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getDataChart())
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("")
    public ApiResponse<Void> updateMentalRecord(@RequestBody @Valid MentalRecordUpdateDTO mentalRecordDTO) throws ParseException {
        mentalRecordService.updateMentalRecord(mentalRecordDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }
    //Test only
    @GetMapping("/{id}")
    public ApiResponse<MentalRecordResponseDTO> getMentalRecordById(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getMentalRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<MentalRecordResponseDTO> deleteMentalRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result( mentalRecordService.deleteMentalRecord(id))
                        .build()).getBody();
    }
}