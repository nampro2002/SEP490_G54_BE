package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @ToString.Exclude
    @JsonIgnore
    private AppUser appUserId;

    private Date weekStart;

    @Enumerated(EnumType.STRING)
    private TypeActivity planType;
    @Enumerated(EnumType.STRING)
    private TypeActivity actualType;

    private Float planDuration;

    private Float actualDuration;

    private Date date;

}
