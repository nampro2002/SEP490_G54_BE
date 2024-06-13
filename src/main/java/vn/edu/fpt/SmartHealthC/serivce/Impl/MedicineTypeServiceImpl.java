package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
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
    public MedicineType getMedicineTypeById(Integer id) {

        Optional<MedicineType> medicineType = medicineTypeRepository.findById(id);
        if(medicineType.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }

        return medicineType.get();
    }

    @Override
    public List<MedicineType> getAllMedicineTypes() {
        return medicineTypeRepository.findAll();
    }

    @Override
    public MedicineType updateMedicineType(Integer id,MedicineType medicineType) {
        MedicineType medicineTypeUpdate = getMedicineTypeById(id);
        medicineTypeUpdate.setDeleted(medicineType.isDeleted());
        medicineTypeUpdate.setDescription(medicineType.getDescription());
        medicineTypeUpdate.setTitle(medicineType.getTitle());
        return medicineTypeRepository.save(medicineTypeUpdate);
    }

    @Override
    public MedicineType deleteMedicineType(Integer id) {
        MedicineType medicineType = getMedicineTypeById(id);
        medicineTypeRepository.deleteById(id);
        return medicineType;
    }
}