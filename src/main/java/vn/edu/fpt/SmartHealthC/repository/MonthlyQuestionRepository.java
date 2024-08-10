package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;

import java.util.List;

public interface MonthlyQuestionRepository extends JpaRepository<MonthlyRecord, Integer> {
    @Query("SELECT w.monthNumber FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND w.monthlyRecordType = 'NEW_MONTH_MARK' order by   w.monthNumber desc limit 1")
    Integer findByAppUser(Integer userId);

    @Query("SELECT DISTINCT w.monthNumber FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND w.monthlyRecordType !='NEW_MONTH_MARK' order by  w.monthNumber desc limit 3")
    List<Integer> find3ByAppUser(Integer userId);
    @Query("SELECT w.monthNumber FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND w.monthlyRecordType = 'NEW_MONTH_MARK' order by  w.monthNumber desc")
    List<Integer> findAllByAppUser(Integer userId);
    @Query("SELECT DISTINCT w.monthNumber FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND w.monthlyRecordType != 'NEW_MONTH_MARK' order by  w.monthNumber desc ")
    List<Integer> find3ByAppUserWeb(Integer userId);

    @Query("SELECT w.monthNumber FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND w.monthNumber <=?2 AND  w.monthlyRecordType = 'NEW_MONTH_MARK' order by  w.monthNumber desc limit 2")
    List<Integer> find2ByAppUserAndMonthNumber(Integer userId, Integer monthNumber);

    @Query("SELECT DISTINCT w.monthNumber FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND  w.monthlyRecordType != 'NEW_MONTH_MARK' order by  w.monthNumber desc limit 12")
    List<Integer> find12ByAppUser(Integer userId);

    @Query("SELECT w FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND w.monthNumber = ?2 AND w.monthlyRecordType != 'NEW_MONTH_MARK'")
    List<MonthlyRecord> findAllByAppUserAndMonthNumber(Integer userId, Integer monthNumber);

    @Query("SELECT COUNT(w.monthNumber) FROM MonthlyRecord w WHERE  w.appUserId.id = ?1 AND w.monthNumber= ?2 order by   w.monthNumber desc limit 1 ")
    Integer countMonthNumberByAppUser(Integer userId, Integer monthNumber);


}
