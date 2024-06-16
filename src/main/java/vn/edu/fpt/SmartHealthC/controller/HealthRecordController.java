package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.serivce.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-records")
public class HealthRecordController {
    @Autowired
    private CardinalRecordService cardinalRecordService;
    @Autowired
    private BloodPressureRecordService bloodPressureRecordService;
    @Autowired
    private WeightRecordService weightRecordService;
    @Autowired
    private MentalRecordService mentalRecordService;
    @Autowired
    private ActivityRecordService activityRecordService;
    @Autowired
    private DietRecordService dietRecordService;
    @Autowired
    private MedicineRecordService medicineRecordService;
    @Autowired
    private StepRecordService stepRecordService;


    @GetMapping("getByAppUser/{id}")
    public ApiResponse<List<CardinalRecordResponseDTO>> getAllCardinalRecords(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<CardinalRecordResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(cardinalRecordService.getAllCardinalRecords(id))
                        .build()).getBody();
    }
//
//    @GetMapping
//    public ApiResponse<List<BloodPressureRecord>> getAllBloodPressureRecords() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<BloodPressureRecord>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(bloodPressureRecordService.getAllBloodPressureRecords())
//                        .build()).getBody();
//    }
//
//    @GetMapping
//    public ApiResponse<List<WeightRecord>> getAllWeightRecords() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<WeightRecord>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(weightRecordService.getAllWeightRecords())
//                        .build()).getBody();
//    }
//
//    @GetMapping
//    public ApiResponse<List<MentalRecord>> getAllMentalRecords() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<MentalRecord>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(mentalRecordService.getAllMentalRecords())
//                        .build()).getBody();
//    }
//    @GetMapping
//    public ApiResponse<List<ActivityRecord>> getAllActivityRecords() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<ActivityRecord>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(activityRecordService.getAllActivityRecords())
//                        .build()).getBody();
//    }
//
//    @GetMapping
//    public ApiResponse<List<DietRecord>> getAllDietRecords() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<DietRecord>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(dietRecordService.getAllDietRecords())
//                        .build()).getBody();
//    }
//    @GetMapping
//    public  ApiResponse<List<MedicineRecord>> getAllMedicineRecords() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<MedicineRecord>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(medicineRecordService.getAllMedicineRecords())
//                        .build()).getBody();
//    }
//    @GetMapping
//    public ApiResponse<List<StepRecord>> getAllStepRecords() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ApiResponse.<List<StepRecord>>builder()
//                        .code(HttpStatus.OK.value())
//                        .result(stepRecordService.getAllStepRecords())
//                        .build()).getBody();
//    }
}
