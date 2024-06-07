package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RuleForPlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.repository.RuleForPlanRepository;
import vn.edu.fpt.SmartHealthC.serivce.RuleForPlanService;

import java.util.List;
import java.util.Optional;

@Service
public class RuleForPlanServiceImpl implements  RuleForPlanService {


    @Autowired
    private RuleForPlanRepository ruleForPlanRepository;
    @Autowired
    private MentalRuleRepository mentalRuleRepository;

    @Autowired
    private MentalRecordRepository mentalRecordRepository;

    @Override
    public RuleForPlan createRuleForPlan(RuleForPlanDTO ruleForPlanDTO) {
        RuleForPlan ruleForPlan =  new RuleForPlan();
        MentalRule mentalRule = mentalRuleRepository.findById(
        ruleForPlanDTO.getRuleId()).orElseThrow(() -> new IllegalArgumentException("MentalRule not found"));

        MentalRecord mentalRecord = mentalRecordRepository.findById(
                ruleForPlanDTO.getPlanId()).orElseThrow(() -> new IllegalArgumentException("MentalRecord not found"));
        ruleForPlan.setPlanId(mentalRecord);
        ruleForPlan.setRuleId(mentalRule);
        return ruleForPlanRepository.save(ruleForPlan);

    }

    @Override
    public Optional<RuleForPlan> getRuleForPlanById(Integer id) {
        return ruleForPlanRepository.findById(id);
    }

    @Override
    public List<RuleForPlan> getAllRuleForPlans() {
        return ruleForPlanRepository.findAll();
    }

    @Override
    public RuleForPlan updateRuleForPlan(RuleForPlanDTO ruleForPlanDTO) {
        RuleForPlan ruleForPlan =  new RuleForPlan();
        MentalRule mentalRule = mentalRuleRepository.findById(
                ruleForPlanDTO.getRuleId()).orElseThrow(() -> new IllegalArgumentException("MentalRule not found"));

        MentalRecord mentalRecord = mentalRecordRepository.findById(
                ruleForPlanDTO.getPlanId()).orElseThrow(() -> new IllegalArgumentException("MentalRecord not found"));
        ruleForPlan.setId(ruleForPlanDTO.getId());
        ruleForPlan.setPlanId(mentalRecord);
        ruleForPlan.setRuleId(mentalRule);
        return ruleForPlanRepository.save(ruleForPlan);
    }

    @Override
    public void deleteRuleForPlan(Integer id) {
        ruleForPlanRepository.deleteById(id);
    }
}
