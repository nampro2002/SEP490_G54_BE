package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ApiResponse<DietRecord> createDietRecord(@RequestBody @Valid DietRecordCreateDTO dietRecordDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.createDietRecord(dietRecordDTO))
                        .build()).getBody();
    }

    @GetMapping("/mobile/chart")
    public ApiResponse<DietResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getDataChart())
                        .build()).getBody();
    }


    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse<List<DietRecordListResDTO>> getAllDietRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<DietRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.getAllDietRecords(id))
                        .build()).getBody();
    }

    @PutMapping("")
    public ApiResponse<DietRecord> updateDietRecord(@RequestBody @Valid DietRecordUpdateDTO dietRecordDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<DietRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(dietRecordService.updateDietRecord(dietRecordDTO))
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