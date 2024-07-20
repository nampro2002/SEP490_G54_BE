package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MentalRecordRepository extends JpaRepository<MentalRecord, Integer> {
    @Query("SELECT DISTINCT m.date FROM MentalRecord m WHERE m.appUserId.id = ?1")
    List<Date> findDistinctDate(Integer userId);
    @Query("SELECT m FROM MentalRecord m WHERE m.appUserId.id = ?1 AND m.date = ?2")
    List<MentalRecord> findByAppUserIdAndDate(Integer userId, Date date);

    @Query("SELECT m FROM MentalRecord m WHERE m.appUserId.id = ?1 AND m.weekStart = ?2")
    List<MentalRecord> findByAppUserIdAndWeekStart(Integer userId, Date date);

    @Query("SELECT m FROM MentalRecord m WHERE  m.appUserId.id = ?1")
    List<MentalRecord> findByAppUserId(Integer userId);

    @Query("SELECT m FROM MentalRecord m WHERE m.date = ?1 AND m.appUserId.id = ?2 AND m.mentalRule.id= ?3 order by  m.date limit 1")
    Optional<MentalRecord> findByDateAndMentalRule(Date date, Integer userId, Integer mentalRuleId);
}
