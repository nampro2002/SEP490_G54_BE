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
public class MedicineRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appUserId;

    private Date weekStart;
//
//    @Enumerated(EnumType.STRING)
//    private TypeMedicine type;

    @ManyToOne
    @JoinColumn(name = "medicineTypeId")
    private MedicineType medicineType;

    private Date date;

    private Boolean status;


//    @OneToMany(mappedBy = "medicinePlanId")
//    private List<MedicineTypePlan> medicineTypePlans;

}
