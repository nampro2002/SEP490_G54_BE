package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalTypeTimeMeasureDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.serivce.CardinalRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/cardinal-records")
public class CardinalRecordController {

    @Autowired
    private CardinalRecordService cardinalRecordService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ApiResponse<CardinalRecord> createCardinalRecord(@RequestBody @Valid CardinalRecordDTO cardinalRecordDTO) throws ParseException {

        CardinalRecord createdCardinalRecord = cardinalRecordService.createCardinalRecord(cardinalRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdCardinalRecord)
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/chart")
    public ApiResponse<CardinalChartResponseDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalChartResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getDataChart())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/get-time-measure-done")
    public ApiResponse<CardinalTypeTimeMeasureDTO> getTimeMeasureDone() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalTypeTimeMeasureDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getTimeMeasureDone())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') or hasAuthority('MEDICAL_SPECIALIST')")
    @GetMapping("/web/weekly-record/{id}")
    public ApiResponse< List<CardinalRecordResponseDTO>> getAllCardinalRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.< List<CardinalRecordResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getAllCardinalRecords(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateCardinalRecord(@PathVariable Integer id, @RequestBody @Valid CardinalRecordDTO cardinalRecordDTO) {
        cardinalRecordService.updateCardinalRecord(id, cardinalRecordDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }

    //Test only

    @GetMapping("/{id}")
    public ApiResponse<CardinalRecord> getCardinalRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getCardinalRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @GetMapping("/get-all")
    public ApiResponse< List<CardinalRecord>> getAllCardinalRecordsVip() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.< List<CardinalRecord>>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getAllCardinalRecordsVip())
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<CardinalRecord> deleteCardinalRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<CardinalRecord>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.deleteCardinalRecord(id))
                        .build()).getBody();
    }
}