package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;
import vn.edu.fpt.SmartHealthC.repository.MedicineTypeRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineTypeServiceImpl implements MedicineTypeService {

    @Autowired
    private MedicineTypeRepository medicineTypeRepository;

    @Override
    public MedicineType createMedicineType(MedicineType medicineType) {
        return medicineTypeRepository.save(medicineType);
    }

    @Override
    public Optional<MedicineType> getMedicineTypeById(Integer id) {
        return medicineTypeRepository.findById(id);
    }

    @Override
    public List<MedicineType> getAllMedicineTypes() {
        return medicineTypeRepository.findAll();
    }

    @Override
    public MedicineType updateMedicineType(MedicineType medicineType) {
        return medicineTypeRepository.save(medicineType);
    }

    @Override
    public void deleteMedicineType(Integer id) {
        medicineTypeRepository.deleteById(id);
    }
}