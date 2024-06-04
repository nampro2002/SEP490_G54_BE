package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

public interface ForgetPasswordCodeRepository extends JpaRepository<ForgetPasswordCode, Integer> {
}
