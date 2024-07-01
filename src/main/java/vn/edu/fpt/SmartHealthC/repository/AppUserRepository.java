package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    //name contain search
    @Query("SELECT u FROM AppUser u WHERE u.webUser.id = ?1 AND LOWER(u.name) LIKE %?2%")
    Page<AppUser> findAllByUserId(Integer id, String search, Pageable pageable);

    @Query("SELECT u FROM AppUser u WHERE u.accountId.isActive = false AND u.accountId.type = ?1")
    Page<AppUser> findAllInactiveAccountUser(TypeAccount type, Pageable paging);
    //native query
//    @Query(value = "SELECT a.id, a.dob,a.gender,a.height,a.medical_specialist_note,a.name,a.phone_number,a.weight,a.account_id,a.cic,a.web_user_id FROM smarthealthc.app_user a left join web_user w on a.web_user_id = w.id left join account acc on w.account_id = acc.id where a.web_user_id is null or acc.is_deleted = true", nativeQuery = true)
    @Query(value = "SELECT a.id, a.dob, a.gender, a.height, a.medical_specialist_note, a.name, a.phone_number, a.weight, a.account_id, a.cic, a.web_user_id FROM account ac JOIN app_user a ON ac.id = a.account_id LEFT JOIN web_user w ON a.web_user_id = w.id LEFT JOIN account acc ON w.account_id = acc.id WHERE ac.is_active = 1 AND (a.web_user_id IS NULL OR acc.is_deleted = true) ORDER BY ac.id ASC", nativeQuery = true)
    Page<AppUser> findAllAccountUserNotAssign(Pageable paging);
    @Query("SELECT u FROM AppUser u WHERE u.accountId.email = ?1")
    Optional<AppUser> findByAccountEmail(String email);
    @Query("SELECT u FROM AppUser u WHERE u.id = ?1 AND u.accountId.isActive = true")
    Optional<AppUser> findByIdActivated(Integer appUserId);
    //deleted = false
    @Query("SELECT u FROM AppUser u WHERE u.accountId.isActive = true AND u.accountId.isDeleted = false")
    List<AppUser> findAllValidUser();
}
