package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;

import java.util.List;
import java.util.Optional;

public interface UserMedicalHistoryRepository extends JpaRepository<UserMedicalHistory, Integer> {
    @Query("SELECT m FROM UserMedicalHistory m WHERE m.appUserId.id =?1 and (m.conditionId.type = 'CARDINAL' or m.conditionId.type = 'RESPIRATORY'or m.conditionId.type='ARTHRITIS') order by m.id limit 1")
    Optional<UserMedicalHistory> findByAppUser(Integer appUserId);

}
