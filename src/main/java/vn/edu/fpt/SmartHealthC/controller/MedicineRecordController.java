package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicinePLanResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicinePlanPerDayResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicineResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/medicine-records")
public class MedicineRecordController {

    @Autowired
    private MedicineRecordService medicineRecordService;
    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping
    public ApiResponse<Void>  createMedicineRecord(@RequestBody @Valid List<MedicineRecordCreateDTO> medicineRecordDTO) throws ParseException {
        medicineRecordService.createMedicineRecord(medicineRecordDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .result(null)
                        .build()).getBody();
    }


    @GetMapping("/web/weekly-record/{id}")
    public  ApiResponse<List<MedicineRecordListResDTO>> getAllMedicineRecordsByAppUserId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicineRecordListResDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getAllMedicineRecords(id))
                        .build()).getBody();
    }

    @GetMapping("/mobile/check-plan/{weekStart}")
    public ApiResponse<Boolean> checkPlanPerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Boolean>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.checkPlanPerDay(weekStart))
                        .build()).getBody();
    }

    @GetMapping("/mobile/chart")
    public ApiResponse<MedicineResponseChartDTO> getDataChart() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineResponseChartDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getDataChart())
                        .build()).getBody();
    }
    @GetMapping("/mobile/medicine-day/{weekStart}")
    public ApiResponse<List<MedicinePlanPerDayResponse>> getMedicinePerDay(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicinePlanPerDayResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getMedicinePerDay(weekStart))
                        .build()).getBody();
    }

    @GetMapping("/mobile/plan-medicine/{weekStart}")
    public  ApiResponse<List<MedicinePLanResponseDTO> > getAllMedicinePlanRecord(@PathVariable String weekStart) throws ParseException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MedicinePLanResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getAllMedicinePlans(weekStart))
                        .build()).getBody();
    }

    @PutMapping("")
    public ApiResponse<MedicineRecordResponseDTO> updateMedicineRecord(@RequestBody @Valid MedicineRecordUpdateDTO medicineRecordDTO) throws ParseException {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.updateMedicineRecord(medicineRecordDTO))
                        .build()).getBody();
    }
    //Test only
    @GetMapping("/{id}")
    public ApiResponse<MedicineRecordResponseDTO> getMedicineRecordById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.getMedicineRecordById(id))
                        .build()).getBody();
    }
    //Test only
    @DeleteMapping("/{id}")
    public ApiResponse<MedicineRecordResponseDTO> deleteMedicineRecord(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MedicineRecordResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(medicineRecordService.deleteMedicineRecord(id))
                        .build()).getBody();
    }
}