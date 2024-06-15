package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;

import java.util.Date;
import java.util.List;

public interface ForgetPasswordCodeRepository extends JpaRepository<ForgetPasswordCode, Integer> {

}
