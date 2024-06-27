package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ApiResponse<Void> createMentalRecord(@RequestBody @Valid MentalRecordCreateDTO mentalRecordDTO) throws ParseException {
        mentalRecordService.createMentalRecord(mentalRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }


    @GetMapping("/mobile/mental-rule/{weekStart}")
    public ApiResponse<List<MentalRule>> getMentalRuleByWeek(@PathVariable String weekStart) throws ParseException {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRule>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getListMentalPerWeek(weekStart))
                        .build()).getBody();
    }
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }

    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<MentalRecordListResDTO>> getAllMentalRecordsByAppUserId(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getAllMentalRecords(id))
                        .build()).getBody();
    }

    @GetMapping("/mobile/chart")
    public ApiResponse<MentalResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRecordService.getDataChart())
                        .build()).getBody();
    }


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