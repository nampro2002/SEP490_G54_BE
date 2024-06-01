package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicine;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser AppUserId;

    private Date WeekStart;

    @Enumerated(EnumType.STRING)
    private TypeMedicine Type;

    private Float Hour;

    private Date Date;

    private Boolean Status;

    @OneToMany(mappedBy = "MedicinePlanId")
    private List<MedicineTypePlan> MedicineTypePlans;

}
