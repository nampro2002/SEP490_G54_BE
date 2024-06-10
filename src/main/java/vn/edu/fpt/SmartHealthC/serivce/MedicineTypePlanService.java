package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypePlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineTypePlan;

import java.util.List;
import java.util.Optional;

public interface MedicineTypePlanService {
    MedicineTypePlan createMedicineTypePlan(MedicineTypePlanDTO medicineTypePlanDTO);
    MedicineTypePlan getMedicineTypePlanById(Integer id);
    List<MedicineTypePlan> getAllMedicineTypePlans();
    MedicineTypePlan updateMedicineTypePlan(Integer id, MedicineTypePlanDTO  medicineTypePlanDTOs);
    MedicineTypePlan deleteMedicineTypePlan(Integer id);
}
