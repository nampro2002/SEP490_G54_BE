package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;

public interface FormQuestionRepository extends JpaRepository<FormQuestion, Integer> {
    @Query("SELECT f FROM FormQuestion f WHERE LOWER(f.question) LIKE %?1% ")
    Page<FormQuestion> findAll(Pageable paging, String search);
}