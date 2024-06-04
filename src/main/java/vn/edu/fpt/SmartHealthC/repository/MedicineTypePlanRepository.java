package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineTypePlan;
import vn.edu.fpt.SmartHealthC.domain.entity.RuleForPlan;

public interface MedicineTypePlanRepository extends JpaRepository<MedicineTypePlan, Integer> {
}
