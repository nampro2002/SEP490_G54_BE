package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String Title;
    private String Description;

    @OneToMany(mappedBy = "RuleId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<RuleForPlan> RuleForPlans;

}
