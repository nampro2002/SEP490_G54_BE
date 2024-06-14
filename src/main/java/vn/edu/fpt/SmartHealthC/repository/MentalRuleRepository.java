package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;

import java.util.List;

public interface MentalRuleRepository extends JpaRepository<MentalRule, Integer> {
    @Query("SELECT m FROM MentalRule m WHERE m.isDeleted = false AND LOWER(m.title) LIKE %?1% ")
    Page<MentalRule> findAllNotDeleted(Pageable paging, String search);

    @Query("SELECT m FROM MentalRule m WHERE m.isDeleted = false")
    List<MentalRule> findAllNotDeleted();
}
