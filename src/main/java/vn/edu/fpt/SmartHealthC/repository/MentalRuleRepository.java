package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;

public interface MentalRuleRepository extends JpaRepository<MentalRule, Integer> {
}
