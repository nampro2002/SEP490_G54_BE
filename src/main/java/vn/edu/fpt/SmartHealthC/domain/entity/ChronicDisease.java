package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeChronicDisease;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChronicDisease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeChronicDisease type;

    @OneToMany(mappedBy = "conditionId")
    private List<UserChronicDisease> userChronicDiseases;


}
