package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeCardinalIndex;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardinalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @JsonIgnore
    @ToString.Exclude
    private AppUser appUserId;

    private Date weekStart;
    private Date date;
    @Enumerated(EnumType.STRING)
    private TypeTimeMeasure timeMeasure;
    private Float Cholesterol;
    private Float BloodSugar;
    private Float HBA1C;


}
