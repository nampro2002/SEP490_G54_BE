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
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);

    //find account by email deleted = false
    @Query("SELECT a FROM Account a WHERE a.email = ?1 AND a.isDeleted = false")
    Optional<Account> findAccountByEmail(String email);
//    WHERE a.isDeleted = false
    @Query("SELECT a FROM Account a")
    List<Account> findAllNotDeleted();

    //find all account type = USER &  account.isactive = true & isDeleted = false
    @Query("SELECT a FROM Account a WHERE a.type = ?1 AND a.isActive = true AND a.isDeleted = false")
    List<Account> findAllAccountAppUser(TypeAccount type);
    @Query("SELECT a FROM Account a WHERE a.type = ?1 AND a.isActive = false AND a.isDeleted = false")
    Page<AppUser> findAllInactiveAccountUser(TypeAccount type, Pageable paging);
}
