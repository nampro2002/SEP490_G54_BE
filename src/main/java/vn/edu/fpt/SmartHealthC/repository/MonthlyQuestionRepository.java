package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;

public interface MonthlyQuestionRepository extends JpaRepository<MonthlyRecord, Integer> {
}
