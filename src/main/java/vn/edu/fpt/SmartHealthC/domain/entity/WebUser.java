package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.RoleWebUser;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account AccountId;

    private String UserName;

    @Enumerated(EnumType.STRING)
    private RoleWebUser Type;

    private String PhoneNumber;

    @OneToMany(mappedBy = "WebUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Question> Question;

}
