package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;

public interface MedicineTypeRepository extends JpaRepository<MedicineType, Integer> {
    }
