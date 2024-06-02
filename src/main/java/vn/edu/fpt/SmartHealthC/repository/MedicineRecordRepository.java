package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
@Repository
public interface MedicineRecordRepository extends JpaRepository<MedicineRecord, Integer> {
}
