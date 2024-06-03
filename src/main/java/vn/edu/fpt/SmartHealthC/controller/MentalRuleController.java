package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<MentalRule> createMentalRule(@RequestBody MentalRule mentalRule) {
        MentalRule createdMentalRule = mentalRuleService.createMentalRule(mentalRule);
        return ResponseEntity.ok(createdMentalRule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentalRule> getMentalRuleById(@PathVariable Integer id) {
        Optional<MentalRule> mentalRule = mentalRuleService.getMentalRuleById(id);
        return mentalRule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MentalRule>> getAllMentalRules() {
        List<MentalRule> mentalRules = mentalRuleService.getAllMentalRules();
        return ResponseEntity.ok(mentalRules);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentalRule> updateMentalRule(@PathVariable Integer id, @RequestBody MentalRule mentalRule) {
        mentalRule.setId(id);
        MentalRule updatedMentalRule = mentalRuleService.updateMentalRule(mentalRule);
        return ResponseEntity.ok(updatedMentalRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentalRule(@PathVariable Integer id) {
        mentalRuleService.deleteMentalRule(id);
        return ResponseEntity.noContent().build();
    }
}
