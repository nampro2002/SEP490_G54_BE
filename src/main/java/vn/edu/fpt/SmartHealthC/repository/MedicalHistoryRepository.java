package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
    @Query("SELECT m FROM MedicalHistory m WHERE m.isDeleted = false AND LOWER(m.name) LIKE %?1% ")
    Page<MedicalHistory> findAllNotDeleted(Pageable paging, String search);
    @Query("SELECT m FROM MedicalHistory m WHERE m.isDeleted = false")
    List<MedicalHistory> findAllNotDeleted();
}
