package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.NumeralRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

public interface NumeralRecordRepository extends JpaRepository<NumeralRecord, Integer> {
}
