package vn.edu.fpt.SmartHealthC.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accessToken;

    private Date  accessExpiryTime;

    private String refreshToken;

    private Date refreshExpiryTime;

    private String deviceToken;
    @Enumerated(EnumType.STRING)
    private TypeLanguage language;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountId;
}
