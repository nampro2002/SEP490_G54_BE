package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;
import vn.edu.fpt.SmartHealthC.domain.entity.RuleForPlan;

public interface RuleForPlanRepository extends JpaRepository<RuleForPlan, Integer> {
}
