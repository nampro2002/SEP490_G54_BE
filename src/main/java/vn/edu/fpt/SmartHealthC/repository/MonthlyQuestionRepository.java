package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

public interface MonthlyQuestionRepository extends JpaRepository<MonthlyQuestion, Integer> {
}