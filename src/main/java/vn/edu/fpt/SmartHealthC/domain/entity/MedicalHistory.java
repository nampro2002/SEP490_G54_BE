package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer id;

    private String name;

    private String nameEn;

    @Enumerated(EnumType.STRING)
    private TypeMedicalHistory type;
    private String imageUrl;

    @OneToMany(mappedBy = "conditionId")
    @JsonIgnore
    @ToString.Exclude
    private List<UserMedicalHistory> userMedicalHistories;
    private boolean isDeleted = false;

}
