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
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser AppUserId;

    private Date MonthStart;

    private int Positivity;

    private int Activity;

    private int Diet;

    private int Medication;

    private int ActivityPlanning;

    private int ActivityHabit;

    private int HealthyDiet;

    private int VegetablePrioritization;

    private int DietHabit;

    private int PlanCompliance;

    private int MedicationHabit;


}
