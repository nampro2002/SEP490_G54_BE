package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StepRecordRepository extends JpaRepository<StepRecord, Integer> {
    @Query("SELECT DISTINCT s.weekStart FROM StepRecord s WHERE s.appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);

    @Query("SELECT s FROM StepRecord s WHERE s.appUserId.id = ?1 AND s.weekStart = ?2")
    List<StepRecord> findByAppUserIdAndWeekStart(Integer userId, Date date);

    @Query("SELECT s FROM StepRecord s WHERE s.appUserId.id = ?1 ")
    List<StepRecord> findByAppUserId(Integer userId);

    @Query("SELECT a FROM StepRecord a WHERE a.appUserId.id = ?1 and a.actualValue != 0  order by a.date desc limit 5")
    List<StepRecord> find5RecordByIdUser(Integer userId);
}
