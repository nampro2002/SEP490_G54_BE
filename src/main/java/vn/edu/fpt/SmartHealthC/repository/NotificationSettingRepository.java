package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.entity.NotificationSetting;

import java.util.List;
import java.util.Optional;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Integer> {
    @Query("SELECT ns FROM NotificationSetting ns WHERE ns.accountId = ?1")
    List<NotificationSetting> findByAccountId(Integer id);
    @Query("SELECT ns FROM NotificationSetting ns WHERE ns.accountId = ?1 AND ns.typeNotification = ?2")
    Optional<NotificationSetting> findByAccountIdAndType(Integer id, TypeNotification typeNotification);
}
