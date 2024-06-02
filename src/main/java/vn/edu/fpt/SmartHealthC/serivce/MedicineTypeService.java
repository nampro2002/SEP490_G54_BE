package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;

import java.util.List;
import java.util.Optional;

public interface MedicineTypeService {
    MedicineType createMedicineType(MedicineType medicineType);
    Optional<MedicineType> getMedicineTypeById(Integer id);
    List<MedicineType> getAllMedicineTypes();
    MedicineType updateMedicineType(MedicineType medicineType);
    void deleteMedicineType(Integer id);
}