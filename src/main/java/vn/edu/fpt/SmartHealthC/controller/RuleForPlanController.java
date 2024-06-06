package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RuleForPlanDTO;
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
    public ResponseEntity<RuleForPlan> createRuleForPlan(@RequestBody RuleForPlanDTO ruleForPlanDTO) {

        RuleForPlan createdRuleForPlan= ruleForPlanService.createRuleForPlan(ruleForPlanDTO);
        return ResponseEntity.ok(createdRuleForPlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleForPlan> getRuleForPlanById(@PathVariable Integer id) {
        Optional<RuleForPlan> ruleForPlan = ruleForPlanService.getRuleForPlanById(id);
        return ruleForPlan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RuleForPlan>> getAllRuleForPlans() {
        List<RuleForPlan> ruleForPlans = ruleForPlanService.getAllRuleForPlans();
        return ResponseEntity.ok(ruleForPlans);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleForPlan> updateRuleForPlan(@PathVariable Integer id, @RequestBody RuleForPlanDTO ruleForPlanDTO) {
        ruleForPlanDTO.setId(id);
        RuleForPlan updatedRuleForPlan = ruleForPlanService.updateRuleForPlan(ruleForPlanDTO);
        return ResponseEntity.ok(updatedRuleForPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuleForPlan(@PathVariable Integer id) {
        ruleForPlanService.deleteRuleForPlan(id);
        return ResponseEntity.noContent().build();
    }
}