package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account accountId;
    @Enumerated(EnumType.STRING)
    private TypeNotification typeNotification;

    private boolean status;

}
