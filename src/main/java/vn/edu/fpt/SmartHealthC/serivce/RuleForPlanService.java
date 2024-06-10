package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.RuleForPlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.RuleForPlan;

import java.util.List;
import java.util.Optional;

public interface RuleForPlanService {
    RuleForPlan createRuleForPlan( RuleForPlanDTO ruleForPlanDTO);
    RuleForPlan getRuleForPlanById(Integer id);
    List<RuleForPlan> getAllRuleForPlans();
    RuleForPlan updateRuleForPlan(Integer id, RuleForPlanDTO  ruleForPlanDTO);
    RuleForPlan deleteRuleForPlan(Integer id);
}
