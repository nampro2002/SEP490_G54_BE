package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRuleRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.LessonResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRuleResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
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
    public ApiResponse<MentalRuleResponseDTO> createMentalRule(@RequestBody @Valid MentalRuleRequestDTO mentalRule) {

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

    @GetMapping("/web/others")
    public ApiResponse<ResponsePaging<List<MentalRuleResponseDTO>>> getAllMentalRules(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<MentalRuleResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRuleService.getAllMentalRules(pageNo-1, search))
                        .build()).getBody();
    }


    @GetMapping("/mobile")
    public ApiResponse<List<MentalRuleResponseDTO>> getAllMentalRulesMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<MentalRuleResponseDTO>>builder()
                        .code(HttpStatus.OK.value())
                        .result(mentalRuleService.getAllMentalRulesMobile())
                        .build()).getBody();
    }


    @PutMapping("/{id}")
    public ApiResponse<MentalRuleResponseDTO> updateMentalRule( @PathVariable Integer id,@RequestBody @Valid MentalRuleRequestDTO mentalRule) {
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
