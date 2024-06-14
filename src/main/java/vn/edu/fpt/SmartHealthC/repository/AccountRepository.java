package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);

    //find account by email deleted = false
    @Query("SELECT a FROM Account a WHERE a.email = ?1 AND a.isDeleted = false")
    Optional<Account> findAccountByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.isDeleted = false")
    List<Account> findAllNotDeleted();
}
