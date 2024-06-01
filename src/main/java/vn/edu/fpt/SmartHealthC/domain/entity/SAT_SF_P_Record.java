package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SAT_SF_P_Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser AppUserId;

    private Date MonthStart;

    private int OverallPoint;

    private int LifePursuit;

    private int Planning;

    private int RightDecision;

    private int PriorityFocus;

    private int HealthyEnvironment;


}
