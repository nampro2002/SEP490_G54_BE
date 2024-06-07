package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeMedicalHistory type;

    @OneToMany(mappedBy = "conditionId")
    private List<UserMedicalHistory> userMedicalHistories;


}
