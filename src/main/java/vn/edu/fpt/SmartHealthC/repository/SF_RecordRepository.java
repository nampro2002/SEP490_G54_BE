package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SF_Record;

public interface SF_RecordRepository extends JpaRepository<SF_Record, Integer> {
}
