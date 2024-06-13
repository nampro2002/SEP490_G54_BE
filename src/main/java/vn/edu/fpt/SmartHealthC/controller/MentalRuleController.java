package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
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
    public ApiResponse<MentalRule> createMentalRule(@RequestBody MentalRule mentalRule) {
        MentalRule createdMentalRule = mentalRuleService.createMentalRule(mentalRule);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<MentalRule>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdMentalRule)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<MentalRule> getMentalRuleById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRule>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRuleService.getMentalRuleById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<MentalRule>> getAllMentalRules() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRule>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRuleService.getAllMentalRules())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<MentalRule> updateMentalRule( @PathVariable Integer id,@RequestBody MentalRule mentalRule) {
        MentalRule updatedMentalRule = mentalRuleService.updateMentalRule(id,mentalRule);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRule>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedMentalRule)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<MentalRule> deleteMentalRule(@PathVariable Integer id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<MentalRule>builder()
                        .code(HttpStatus.OK.value())
                        .result(  mentalRuleService.deleteMentalRule(id))
                        .build()).getBody();
    }
}
