package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyQuestionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @ToString.Exclude
    @JsonIgnore
    private AppUser appUserId;

    private int monthNumber;
    @Column(name = "total_SAT")
    private float totalSAT;

    @Column(name = "sat_sf_c_activity_point")
    private float sat_sf_c_activityPoint;

    @Column(name = "sat_sf_c_positivity_point")
    private float sat_sf_c_positivityPoint;

    @Column(name = "sat_sf_c_support_point")
    private float sat_sf_c_supportPoint;

    @Column(name = "sat_sf_c_experience_point")
    private float sat_sf_c_experiencePoint;

    @Column(name = "sat_sf_p_life_value")
    private float sat_sf_p_lifeValue;

    @Column(name = "sat_sf_p_target_and_action")
    private float sat_sf_p_targetAndAction;

    @Column(name = "sat_sf_p_decision")
    private float sat_sf_p_decision;

    @Column(name = "sat_sf_p_build_plan")
    private float sat_sf_p_buildPlan;

    @Column(name = "sat_sf_p_healthy_environment")
    private float sat_sf_p_healthyEnvironment;

    @Column(name = "sat_sf_i_e_activity_point")
    private float sat_sf_i_e_activityPoint;

    @Column(name = "sat_sf_i_e_activity_stress_point")
    private float sat_sf_i_e_activityStressPoint;

    @Column(name = "sat_sf_i_e_activity_substantial_point")
    private float sat_sf_i_e_activitySubstantialPoint;

    @Column(name = "sat_sf_i_e_energy")
    private float sat_sf_i_e_energy;

    @Column(name = "sat_sf_i_e_motivation")
    private float sat_sf_i_e_motivation;

    @Column(name = "sat_sf_i_e_plan_check")
    private float sat_sf_i_e_planCheck;

    @Column(name = "sat_sf_c_total")
    private float sat_sf_c_total;

    @Column(name = "sat_sf_p_total")
    private float sat_sf_p_total;

    @Column(name = "sat_sf_i_total")
    private float sat_sf_i_total;

    @Column(name = "total_SF")
    private float totalSF;

    @Column(name = "sf_mental_point")
    private float sf_mentalPoint;

    @Column(name = "sf_activity_plan_point")
    private float sf_activity_planPoint;

    @Column(name = "sf_activity_habit_point")
    private float sf_activity_habitPoint;

    @Column(name = "sf_diet_healthy_point")
    private float sf_diet_healthyPoint;

    @Column(name = "sf_diet_vegetable_point")
    private float sf_diet_vegetablePoint;

    @Column(name = "sf_diet_habit_point")
    private float sf_diet_habitPoint;

    @Column(name = "sf_medicine_follow_plan_point")
    private float sf_medicine_followPlanPoint;

    @Column(name = "sf_medicine_habit_point")
    private float sf_medicine_habitPoint;

    @Column(name = "sf_mental_model_point")
    private float sf_mental_modelPoint;

    @Column(name = "sf_activity_model_point")
    private float sf_activity_modelPoint;

    @Column(name = "sf_diet_model_point")
    private float sf_diet_modelPoint;

    @Column(name = "sf_medicine_model_point")
    private float sf_medicine_modelPoint;

}
