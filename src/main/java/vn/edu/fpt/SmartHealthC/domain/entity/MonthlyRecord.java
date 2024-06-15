package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @JsonIgnore
    @ToString.Exclude
    private AppUser appUserId;

    private Date monthStart;
    @Enumerated(EnumType.STRING)
    private MonthlyRecordType monthlyRecordType;

    private int questionNumber;

    private String question;

    private int answer;

}
