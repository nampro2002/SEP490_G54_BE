package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodPressureRecord {
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

    //Tâm thu
    private Float systole;

    //Tâm trương
    private Float diastole;




}
