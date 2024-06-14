package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.Date;
import java.util.List;

public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Integer> {
    @Query("SELECT DISTINCT a.weekStart FROM ActivityRecord a WHERE a.appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);

    @Query("SELECT a FROM ActivityRecord a WHERE a.weekStart = ?1 AND a.appUserId.id = ?2")
    List<ActivityRecord> findByWeekStart(Date weekStart, Integer userId);
}
