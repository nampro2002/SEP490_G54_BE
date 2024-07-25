package vn.edu.fpt.SmartHealthC.repository;

import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyQuestionPoint;
import vn.edu.fpt.SmartHealthC.domain.entity.SF_Record;

import java.util.Optional;

public interface MonthlyQuestionPointRepository extends JpaRepository<MonthlyQuestionPoint, Integer> {
    @Query("SELECT m FROM MonthlyQuestionPoint m WHERE  m.appUserId.id = ?2 and m.monthNumber=?1 order by m.id limit 1")
    Optional<MonthlyQuestionPoint> findByMonthAndUser(Integer month, Integer userId);
}
