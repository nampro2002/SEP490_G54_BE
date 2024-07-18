package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DietRecordRepository extends JpaRepository<DietRecord, Integer> {
    @Query("SELECT DISTINCT weekStart FROM DietRecord WHERE appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer userId);
    @Query("SELECT d FROM DietRecord d WHERE d.weekStart = ?1 AND d.appUserId.id = ?2")
    List<DietRecord> findByWeekStart(Date weekStart, Integer userId);

    @Query("SELECT d FROM DietRecord d WHERE d.date = ?1 AND d.appUserId.id = ?2  ")
    Optional<DietRecord> findByDate(Date date, Integer userId);

    @Query("SELECT d FROM DietRecord d WHERE d.appUserId.id = ?1")
    List<DietRecord> findByAppUser( Integer userId);

    @Query("SELECT d FROM DietRecord d WHERE d.appUserId.id = ?1 and d.weekStart = ?2")
    List<DietRecord> findByAppUserAndDate( Integer userId,Date date);
    @Query("SELECT a FROM DietRecord a WHERE a.appUserId.id = ?1 and a.actualValue != null  order by a.date desc limit 5")
    List<DietRecord> find5RecordByIdUser(Integer userId);
}
