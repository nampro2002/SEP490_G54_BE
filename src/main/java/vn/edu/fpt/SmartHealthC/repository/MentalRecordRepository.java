package vn.edu.fpt.SmartHealthC.repository;

import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentalRecordRepository extends JpaRepository<MentalRecord, Integer> {
}
