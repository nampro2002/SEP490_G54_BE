package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.*;

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

    private String Name;

    @OneToMany(mappedBy = "ConditionId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<UserChronicDisease> UserChronicDiseases;


}
