package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;

import java.util.Date;
import java.util.List;

public interface CardinalRecordRepository extends JpaRepository<CardinalRecord, Integer> {

    @Query("SELECT c FROM CardinalRecord c WHERE c.appUserId.id = ?1")
    List<CardinalRecord> findByAppUserId(Integer appUserId);
    @Query("SELECT DISTINCT c.weekStart FROM CardinalRecord c WHERE c.appUserId.id = ?1")
    List<Date> findDistinctWeek(Integer id);

    @Query("SELECT c FROM CardinalRecord c WHERE c.weekStart = ?1 AND c.appUserId.id = ?2")
    List<CardinalRecord> findByWeekStart( Date record, Integer userId);

    @Query("SELECT c FROM CardinalRecord c WHERE c.date = ?1 AND c.appUserId.id = ?2 AND c.timeMeasure = ?3")
    List<CardinalRecord> findByDateAndTimeMeasure(Date date, Integer userId, TypeTimeMeasure timeMeasure);

//    @Query("SELECT a FROM CardinalRecord a WHERE a.appUserId.id = ?1  order by a.date desc limit 5")
//    List<CardinalRecord> find5RecordByIdUser(Integer userId);
}
