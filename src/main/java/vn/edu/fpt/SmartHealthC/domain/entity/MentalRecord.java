package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser AppUserId;

    private Float Point;

    private Date WeekStart;

    private Date Date;

    @OneToMany(mappedBy = "PlanId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<RuleForPlan> RuleForPlans;


}
