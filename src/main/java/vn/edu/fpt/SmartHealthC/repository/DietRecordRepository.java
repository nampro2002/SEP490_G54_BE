package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

public interface DietRecordRepository extends JpaRepository<DietRecord, Integer> {
}
