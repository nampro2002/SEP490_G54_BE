package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

public interface SAT_SF_C_RecordRepository extends JpaRepository<SAT_SF_C_Record, Integer> {
}
