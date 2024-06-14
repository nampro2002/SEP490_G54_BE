package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;

import java.util.List;

public interface MedicineTypeRepository extends JpaRepository<MedicineType, Integer> {
    @Query("SELECT m FROM MedicineType m WHERE m.isDeleted = false AND LOWER(m.title) LIKE %?1% ")
    Page<MedicineType> findAllNotDeleted(Pageable paging, String search);

    @Query("SELECT m FROM MedicineType m WHERE m.isDeleted = false")
    List<MedicineType> findAllNotDeleted();
}
