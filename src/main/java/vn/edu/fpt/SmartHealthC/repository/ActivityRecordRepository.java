package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Integer> {
    @Query("SELECT DISTINCT a.weekStart FROM ActivityRecord a WHERE a.appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);

    @Query("SELECT a FROM ActivityRecord a WHERE a.weekStart = ?1 AND a.appUserId.id = ?2")
    List<ActivityRecord> findByWeekStart( Date weekStart, Integer userId);

    @Query("SELECT a FROM ActivityRecord a WHERE a.date = ?1 AND a.appUserId.id = ?2 order by  a.id limit 1")
    Optional<ActivityRecord> findByDate(Date date, Integer userId);

    @Query("SELECT a FROM ActivityRecord a WHERE a.appUserId.id = ?1")
    List<ActivityRecord> findRecordByIdUser( Integer userId);

    @Query("SELECT a FROM ActivityRecord a WHERE a.appUserId.id = ?1 and a.actualType != null  order by a.date desc limit 5")
    List<ActivityRecord> find5RecordByIdUser( Integer userId);
}
