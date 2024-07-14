package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.WeekReview;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WeekReviewRepository extends JpaRepository<WeekReview, Integer> {
    @Query("SELECT s FROM WeekReview s WHERE s.appUserId.id = ?1 ")
    List<WeekReview> findByAppUserId(Integer userId);
    @Query("SELECT s FROM WeekReview s WHERE s.appUserId.id = ?1 and s.weekStart = ?2 order by  s.weekStart limit 1")
    Optional<WeekReview> find1ByAppUserIdAndWeekStart(Integer userId,Date weekStart);


    @Query("SELECT s FROM WeekReview s WHERE s.appUserId.id = ?1 order by s.weekStart desc limit 1 ")
    Optional<WeekReview> findNearestWeekStart(Integer userId);
    @Query("SELECT s.weekStart FROM WeekReview s WHERE s.appUserId.id = ?1 order by s.weekStart desc ")
    List<Date> findListWeekStart(Integer userId);

    @Query("SELECT s FROM WeekReview s WHERE s.appUserId.id = ?1 order by s.weekStart desc limit 5 ")
    List<WeekReview> find5NearestWeekStart(Integer userId);
}