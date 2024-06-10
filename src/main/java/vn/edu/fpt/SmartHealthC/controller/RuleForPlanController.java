package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RuleForPlanDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;
import vn.edu.fpt.SmartHealthC.domain.entity.RuleForPlan;
import vn.edu.fpt.SmartHealthC.serivce.RuleForPlanService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rule-plan")
public class RuleForPlanController {

    @Autowired
    private RuleForPlanService ruleForPlanService;

    @PostMapping
    public ApiResponse<RuleForPlan> createRuleForPlan(@RequestBody RuleForPlanDTO ruleForPlanDTO) {

        RuleForPlan createdRuleForPlan= ruleForPlanService.createRuleForPlan(ruleForPlanDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RuleForPlan>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(createdRuleForPlan)
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<RuleForPlan> getRuleForPlanById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RuleForPlan>builder()
                        .code(HttpStatus.OK.value())
                        .result(ruleForPlanService.getRuleForPlanById(id))
                        .build()).getBody();
    }

    @GetMapping
    public ApiResponse<List<RuleForPlan>> getAllRuleForPlans() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<RuleForPlan>>builder()
                        .code(HttpStatus.OK.value())
                        .result(ruleForPlanService.getAllRuleForPlans())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<RuleForPlan> updateRuleForPlan(@PathVariable Integer id,@RequestBody RuleForPlanDTO ruleForPlanDTO) {
        RuleForPlan updatedRuleForPlan = ruleForPlanService.updateRuleForPlan(id,ruleForPlanDTO);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RuleForPlan>builder()
                        .code(HttpStatus.OK.value())
                        .result(updatedRuleForPlan)
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<RuleForPlan> deleteRuleForPlan(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<RuleForPlan>builder()
                        .code(HttpStatus.OK.value())
                        .result( ruleForPlanService.deleteRuleForPlan(id))
                        .build()).getBody();
    }
}