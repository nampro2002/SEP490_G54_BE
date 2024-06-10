package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypePlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.*;
import vn.edu.fpt.SmartHealthC.serivce.MedicineTypePlanService;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineTypePlanServiceImpl implements MedicineTypePlanService {


    @Autowired
    private MedicineTypePlanRepository medicineTypePlanRepository;
    @Autowired
    private MedicineTypeRepository medicineTypeRepository;

    @Autowired
    private MedicineRecordRepository medicineRecordRepository;

    @Override
    public MedicineTypePlan createMedicineTypePlan(MedicineTypePlanDTO medicineTypePlanDTO) {
        MedicineTypePlan medicineTypePlan =  new MedicineTypePlan();
        Optional<MedicineType> medicineType = medicineTypeRepository.findById(medicineTypePlanDTO.getMedicineTypeId());
        if(medicineType.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }
        medicineTypePlan.setMedicineTypeId(medicineType.get());
        Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findById(medicineTypePlanDTO.getMedicinePlanId());
        if(medicineRecord.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }
        medicineTypePlan.setMedicinePlanId(medicineRecord.get());

        return medicineTypePlanRepository.save(medicineTypePlan);

    }

    @Override
    public MedicineTypePlan getMedicineTypePlanById(Integer id) {
        Optional<MedicineTypePlan> medicineTypePlan = medicineTypePlanRepository.findById(id);
        if(medicineTypePlan.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_PLAN_NOT_FOUND);
        }
        return medicineTypePlan.get();
    }

    @Override
    public List<MedicineTypePlan> getAllMedicineTypePlans() {
        return medicineTypePlanRepository.findAll();
    }

    @Override
    public MedicineTypePlan updateMedicineTypePlan(Integer id,MedicineTypePlanDTO medicineTypePlanDTO) {
        MedicineTypePlan medicineTypePlan =  getMedicineTypePlanById(id);
        Optional<MedicineType> medicineType = medicineTypeRepository.findById(medicineTypePlanDTO.getMedicineTypeId());
        if(medicineType.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }
        medicineTypePlan.setMedicineTypeId(medicineType.get());
        Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findById(medicineTypePlanDTO.getMedicinePlanId());
        if(medicineRecord.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }
        medicineTypePlan.setMedicinePlanId(medicineRecord.get());

        return medicineTypePlanRepository.save(medicineTypePlan);
    }

    @Override
    public MedicineTypePlan deleteMedicineTypePlan(Integer id) {
        MedicineTypePlan medicineTypePlan = getMedicineTypePlanById(id);
        medicineTypePlanRepository.deleteById(id);
        return medicineTypePlan;
    }
}
