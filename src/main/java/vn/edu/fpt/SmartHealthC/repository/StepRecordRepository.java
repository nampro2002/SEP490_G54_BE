package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.Date;
import java.util.List;

public interface StepRecordRepository extends JpaRepository<StepRecord, Integer> {
    @Query("SELECT DISTINCT s.weekStart FROM StepRecord s WHERE s.appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);

    @Query("SELECT s FROM StepRecord s WHERE s.appUserId.id = ?1 AND s.weekStart = ?2")
    List<StepRecord> findByAppUserIdAndWeekStart(Integer userId, Date date);
}
