package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Integer> {
    @Query("SELECT DISTINCT weekStart FROM WeightRecord WHERE appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);

    @Query("SELECT w FROM WeightRecord w WHERE w.weekStart = ?1 AND w.appUserId.id = ?2")
    List<WeightRecord> findByWeekStart(Date weekStart, Integer userId);

    @Query("SELECT w FROM WeightRecord w WHERE w.date = ?1 AND w.appUserId.id = ?2")
    List<WeightRecord> findByDate(Date weekStart, Integer userId);

    @Query("SELECT w FROM WeightRecord w WHERE  w.appUserId.id = ?1")
    List<WeightRecord> findAppUser( Integer userId);

    @Query("SELECT w FROM WeightRecord w WHERE  w.appUserId.id = ?1 and  w.weekStart = ?2")
    List<WeightRecord> findAppUserAndWeekStart( Integer userId,Date date);

    @Query("SELECT a FROM WeightRecord a WHERE a.appUserId.id = ?1 and a.weight > 0   order by a.date desc limit 5")
    List<WeightRecord> find5RecordByIdUser(Integer userId);
    @Query("SELECT a FROM WeightRecord a WHERE a.appUserId.id = ?1 order by a.date desc limit 1")
    Optional<WeightRecord> findNewestRecord(Integer id);
}
