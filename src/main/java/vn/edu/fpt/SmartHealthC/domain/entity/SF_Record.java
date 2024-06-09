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
public class SF_Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appUserId;

    private Date monthStart;

    private int positivity;

    private int activity;

    private int diet;

    private int medication;

    private int activityPlanning;

    private int activityHabit;

    private int healthyDiet;

    private int vegetablePrioritization;

    private int dietHabit;

    private int planCompliance;

    private int medicationHabit;


}
