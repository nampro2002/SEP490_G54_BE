package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;

import java.util.List;
import java.util.Optional;

public interface WebUserRepository extends JpaRepository<WebUser, Integer> {
   //find all undeleted and account type is medical specialist
    @Query("SELECT w FROM WebUser w WHERE w.accountId.isDeleted = false AND w.accountId.type = 'MEDICAL_SPECIALIST'")
    List<WebUser> findAllUnDeletedMS();
    @Query("SELECT w FROM WebUser w WHERE w.accountId.email = ?1")
    Optional<WebUser> findByEmail(String email);
}
