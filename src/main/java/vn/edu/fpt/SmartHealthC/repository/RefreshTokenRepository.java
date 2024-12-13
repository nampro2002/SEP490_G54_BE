package vn.edu.fpt.SmartHealthC.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    //find account by email deleted = false
    @Query("SELECT a FROM RefreshToken a WHERE  a.accessToken = ?1 order by a.accessToken desc limit 1")
    Optional<RefreshToken> findRecordByAcToken(String accessToken);

    @Query("SELECT a FROM RefreshToken a WHERE a.refreshToken = ?1 order by a.refreshToken desc limit 1")
    Optional<RefreshToken> findRecordByReToken(String refreshToken);
    @Query("SELECT a FROM RefreshToken a WHERE a.accountId.Id = ?1")
    List<RefreshToken> findRecordByAccountId(Integer accountId);
    @Query("SELECT a FROM RefreshToken a WHERE  a.deviceToken = ?1")
    List<RefreshToken> findRecordBydDeviceToken(String deviceToken);
    @Query("SELECT a FROM RefreshToken a WHERE a.accountId.Id = ?1 and a.deviceToken = ?2")
    Optional<RefreshToken> findByAccountIdAndDevice(Integer accountId, String deviceToken);
}
