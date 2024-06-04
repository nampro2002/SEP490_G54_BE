package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;

public interface SAT_SF_P_RecordRepository extends JpaRepository<SAT_SF_P_Record, Integer> {
}
