package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.Date;
import java.util.List;

public interface DietRecordRepository extends JpaRepository<DietRecord, Integer> {
    @Query("SELECT DISTINCT weekStart FROM DietRecord WHERE appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);
    @Query("SELECT d FROM DietRecord d WHERE d.weekStart = ?1 AND d.appUserId.id = ?2")
    List<DietRecord> findByWeekStart(Date weekStart, Integer userId);
}
