package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;

public interface SAT_SF_I_RecordRepository extends JpaRepository<SAT_SF_I_Record, Integer> {
}
