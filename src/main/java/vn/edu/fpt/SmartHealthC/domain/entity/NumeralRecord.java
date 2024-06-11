package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeCardinalIndex;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumeralRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appUserId;

    private Date weekStart;

    private Date date;
    @Enumerated(EnumType.STRING)
    private TypeTimeMeasure timeMeasure;

    @Enumerated(EnumType.STRING)
    private TypeCardinalIndex typeCardinalIndex;

    private float value;

}
