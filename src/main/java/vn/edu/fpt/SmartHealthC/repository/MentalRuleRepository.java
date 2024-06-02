package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
@Repository
public interface MentalRuleRepository extends JpaRepository<MentalRule, Integer> {
}
