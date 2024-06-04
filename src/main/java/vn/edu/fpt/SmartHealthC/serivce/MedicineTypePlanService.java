package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.MedicineTypePlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineTypePlan;

import java.util.List;
import java.util.Optional;

public interface MedicineTypePlanService {
    MedicineTypePlan createMedicineTypePlan(MedicineTypePlanDTO medicineTypePlanDTO);
    Optional<MedicineTypePlan> getMedicineTypePlanById(Integer id);
    List<MedicineTypePlan> getAllMedicineTypePlans();
    MedicineTypePlan updateMedicineTypePlan( MedicineTypePlanDTO  medicineTypePlanDTOs);
    void deleteMedicineTypePlan(Integer id);
}
