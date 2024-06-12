package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;

import java.util.Date;
import java.util.List;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Integer> {
    @Query("SELECT DISTINCT weekStart FROM WeightRecord WHERE appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);

    @Query("SELECT w FROM WeightRecord w WHERE w.weekStart = ?1 AND w.appUserId.id = ?2")
    List<WeightRecord> findByWeekStart(Date weekStart, Integer userId);
}
