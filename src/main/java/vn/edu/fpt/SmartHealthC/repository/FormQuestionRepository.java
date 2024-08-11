package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;

import java.util.List;
import java.util.Optional;

public interface FormQuestionRepository extends JpaRepository<FormQuestion, Integer> {
    @Query("SELECT f FROM FormQuestion f WHERE LOWER(f.question) LIKE %?1% or  LOWER(f.questionEn) LIKE %?1% ")
    Page<FormQuestion> findAll(Pageable paging, String search);

    @Query("SELECT f FROM FormQuestion f WHERE f.questionNumber = ?1 order by f.id limit 1")
    Optional<FormQuestion> findRecordByQuestionNumber(int number);

    @Query("SELECT f FROM FormQuestion f WHERE f.questionNumber = ?1 AND f.type = ?2 order by f.id limit 1")
    Optional<FormQuestion> findRecordByQuestionNumberAndType(int number, MonthlyRecordType type);

    @Query("SELECT f FROM FormQuestion f WHERE f.type = ?1 order by f.questionNumber asc")
    List<FormQuestion> findRecordByType(MonthlyRecordType type);
}