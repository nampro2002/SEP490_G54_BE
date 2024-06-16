package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    //find account by email deleted = false
    @Query("SELECT a FROM RefreshToken a WHERE  a.accessToken = ?1 ")
    Optional<RefreshToken> findRecordByAcToken(String accessToken);

    @Query("SELECT a FROM RefreshToken a WHERE a.refreshToken = ?1")
    Optional<RefreshToken> findRecordByReToken(String refreshToken);
}
