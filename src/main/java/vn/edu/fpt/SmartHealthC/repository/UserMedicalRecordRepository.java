package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalRecord;

public interface UserMedicalRecordRepository extends JpaRepository<UserMedicalRecord, Integer> {
}