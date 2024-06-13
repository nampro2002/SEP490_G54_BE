package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRuleRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRuleResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.serivce.MentalRuleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mental-rules")
public class MentalRuleController {
    @Autowired
    private MentalRuleService mentalRuleService;

    @PostMapping
    public ApiResponse<MentalRuleResponseDTO> createMentalRule(@RequestBody MentalRuleRequestDTO mentalRule) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MentalRuleResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(mentalRuleService.createMentalRule(mentalRule))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MentalRule> getMentalRuleById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRule>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRuleService.getMentalRuleEntityById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<MentalRuleResponseDTO>> getAllMentalRules() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRuleResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRuleService.getAllMentalRules())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MentalRuleResponseDTO> updateMentalRule( @PathVariable Integer id,@RequestBody MentalRuleRequestDTO mentalRule) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRuleResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRuleService.updateMentalRule(id,mentalRule))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MentalRuleResponseDTO> deleteMentalRule(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRuleResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(  mentalRuleService.deleteMentalRule(id))
                        .build()).getBody();
    }
}
