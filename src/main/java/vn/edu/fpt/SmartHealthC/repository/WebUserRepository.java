package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;

import java.util.List;
import java.util.Optional;

public interface WebUserRepository extends JpaRepository<WebUser, Integer> {
    //find all undeleted and account type is medical specialist
    @Query("SELECT w FROM WebUser w WHERE w.accountId.isDeleted = false AND w.accountId.type = 'MEDICAL_SPECIALIST'")
    List<WebUser> findAllUnDeletedMS();

    @Query("SELECT w FROM WebUser w WHERE w.accountId.email = ?1 order by w.accountId.email limit 1")
    Optional<WebUser> findByEmail(String email);

    @Query("SELECT w FROM WebUser w WHERE w.accountId.type = ?1 AND w.accountId.isDeleted = false AND w.accountId.isActive = false ")
    Page<WebUser> findAllInactiveAccountUser(TypeAccount type, Pageable paging);


    @Query("SELECT w FROM WebUser w WHERE w.accountId.type = ?1  AND LOWER(w.userName) LIKE %?2% AND w.accountId.isDeleted = false AND w.accountId.isActive= true")
    Page<WebUser> findAllUnDeletedDoctor(TypeAccount typeAccount, String lowerCase, Pageable paging);

    @Query("SELECT w FROM WebUser w WHERE w.accountId.type <> ?1  AND LOWER(w.userName) LIKE %?2%  AND w.accountId.isDeleted = false AND w.accountId.isActive= true")
    Page<WebUser> findAllUnDeletedNotDoctor(TypeAccount typeAccount,String lowerCase, Pageable paging);
    @Query("SELECT w FROM WebUser w WHERE w.accountId.type = ?1")
    List<WebUser> findAllByType(TypeAccount typeAccount);
    @Query("SELECT w FROM WebUser w WHERE w.accountId.Id = ?1")
    Optional<WebUser> findByAccountId(Integer webUserId);

}
