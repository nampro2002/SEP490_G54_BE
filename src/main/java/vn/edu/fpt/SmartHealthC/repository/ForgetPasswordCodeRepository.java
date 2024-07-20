package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ForgetPasswordCodeRepository extends JpaRepository<ForgetPasswordCode, Integer> {
    @Query("SELECT a FROM ForgetPasswordCode a WHERE a.accountId = ?1 AND  a.code = BINARY(?2) order by a.code limit 1")
    Optional<ForgetPasswordCode> findRecordByCodeAndAccount(Account account_id , String code) ;
}
