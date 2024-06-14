package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    //name contain search
    @Query("SELECT u FROM AppUser u WHERE LOWER(u.name) LIKE %?1%")
    Page<AppUser> findAll(Pageable pageable, String search);

    @Query("SELECT u FROM AppUser u WHERE u.accountId.isActive = false AND u.accountId.type = ?1")
    Page<AppUser> findAllInactiveAccountUser(TypeAccount type, Pageable paging);
    @Query("SELECT u FROM AppUser u WHERE u.accountId.isActive = true AND u.accountId.type = ?1 AND u.webUser.accountId.isDeleted = true")
    Page<AppUser> findAllAccountUserNotAssign(TypeAccount type, Pageable paging);
}
