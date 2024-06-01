package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineTypePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "medicine_plan_Id")
    private MedicineRecord MedicinePlanId;

    @ManyToOne
    @JoinColumn(name = "medicine_type_Id")
    private MedicineType MedicineTypeId;



}
