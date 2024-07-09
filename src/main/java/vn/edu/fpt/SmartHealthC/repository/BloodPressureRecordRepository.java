package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.Date;
import java.util.List;

public interface BloodPressureRecordRepository extends JpaRepository<BloodPressureRecord, Integer> {
    @Query("SELECT b FROM BloodPressureRecord b WHERE b.appUserId.id = ?1")
    List<BloodPressureRecord> findAllByUserId(Integer id);
    @Query("SELECT DISTINCT b.weekStart FROM BloodPressureRecord b WHERE b.appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer id);
    @Query("SELECT b FROM BloodPressureRecord b WHERE b.weekStart = ?1 AND b.appUserId.id = ?2")
    List<BloodPressureRecord> findByWeekStart(Date weekStart, Integer userId);

    @Query("SELECT b FROM BloodPressureRecord b WHERE b.date = ?1 AND b.appUserId.id = ?2")
    List<BloodPressureRecord> findByDate(Date weekStart, Integer userId);

    @Query("SELECT a FROM BloodPressureRecord a WHERE a.appUserId.id = ?1 order by a.date desc limit 5")
    List<BloodPressureRecord> find5RecordByIdUser(Integer userId);
}
