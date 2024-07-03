package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.WeekReview;

import java.util.List;

public interface WeekReviewRepository extends JpaRepository<WeekReview, Integer> {
    @Query("SELECT s FROM WeekReview s WHERE s.appUserId.id = ?1 ")
    List<WeekReview> findByAppUserId(Integer userId);
}