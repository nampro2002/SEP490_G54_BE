package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;

public interface UserMedicalHistoryRepository extends JpaRepository<UserMedicalHistory, Integer> {
}
