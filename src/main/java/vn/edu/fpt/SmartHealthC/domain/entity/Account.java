package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(unique=true)
    private String Email;

    private String Password;

    @Enumerated(EnumType.STRING)
    private TypeAccount Type;

    private Boolean isActive;

    @OneToMany(mappedBy = "AccountId")
    private List<ForgetPasswordCode> ForgetPasswordCode;

}
