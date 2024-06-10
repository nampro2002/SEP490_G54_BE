package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RuleForPlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
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
        Optional<MentalRule> mentalRule = mentalRuleRepository.findById(ruleForPlanDTO.getRuleId());
        if(mentalRule.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }
        ruleForPlan.setRuleId(mentalRule.get());
        Optional<MentalRecord> mentalRecord = mentalRecordRepository.findById(ruleForPlanDTO.getPlanId());
        if(mentalRecord.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_NOT_FOUND);
        }
        ruleForPlan.setPlanId(mentalRecord.get());
        return ruleForPlanRepository.save(ruleForPlan);

    }

    @Override
    public RuleForPlan getRuleForPlanById(Integer id) {

        Optional<RuleForPlan> ruleForPlan = ruleForPlanRepository.findById(id);
        if(ruleForPlan.isEmpty()) {
            throw new AppException(ErrorCode.RULE_FOR_PLAN_NOTFOUND);
        }
        return ruleForPlan.get();
    }

    @Override
    public List<RuleForPlan> getAllRuleForPlans() {
        return ruleForPlanRepository.findAll();
    }

    @Override
    public RuleForPlan updateRuleForPlan(Integer id,RuleForPlanDTO ruleForPlanDTO) {
        RuleForPlan ruleForPlan =  getRuleForPlanById(id);
        Optional<MentalRule> mentalRule = mentalRuleRepository.findById(ruleForPlanDTO.getRuleId());
        if(mentalRule.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }
        ruleForPlan.setRuleId(mentalRule.get());
        Optional<MentalRecord> mentalRecord = mentalRecordRepository.findById(ruleForPlanDTO.getPlanId());
        if(mentalRecord.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_NOT_FOUND);
        }
        return ruleForPlanRepository.save(ruleForPlan);
    }

    @Override
    public RuleForPlan deleteRuleForPlan(Integer id) {
        RuleForPlan ruleForPlan= getRuleForPlanById(id);
        ruleForPlanRepository.deleteById(id);
        return ruleForPlan;
    }
}
