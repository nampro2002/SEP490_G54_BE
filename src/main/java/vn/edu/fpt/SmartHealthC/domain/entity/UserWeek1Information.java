package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;

@Entity(name = "user_week1_information")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWeek1Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @ToString.Exclude
    @JsonIgnore
    private AppUser appUserId;

    private String intermediateGoal;
    private String endOfYearGoal;

    private String strength;
    private String weakPoint;

    private String closePerson1;
    private String closePerson2;
    @Column(name = "close_person1_message")
    private String closePerson1Message;
    @Column(name = "close_person2_message")
    private String closePerson2Message;
    private String prefrerredEnvironment;
    private String prefrerredTime;
    private String notPreferredLocation;
    private String notPreferredTime;

    private Integer score10;
    private Integer score20;
    private Integer score30;
    private Integer score40;
    private Integer score50;
    private String recentValues;
    @Column(name="influence_on_life")
    private String influenceOnLife;
    private String newValues;
    private String reasonForChanging;

    private String currentEmotion;
    private String whyIfRealistic;
    private String whyIfNotBetterForLife;


    private Boolean noMoreThan2;
    private Boolean todoList;
    private Boolean noProcastinating;
    private Boolean doExercises;

    private String whatIsHealth;
    private String activityCommitment;
    private String dietCommitment;
    private String mentalCommitment;
    private String medicineCommitment;
    private String roadBlock;
    private String solution;
    private String commitment;


}
