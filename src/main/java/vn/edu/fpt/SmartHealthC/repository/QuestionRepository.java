package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.Question;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findAll(Pageable pageable);
}
