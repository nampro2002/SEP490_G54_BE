package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.MedicineTypePlanDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.RuleForPlanDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
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
        MedicineType medicineType = medicineTypeRepository.findById(
                medicineTypePlanDTO.getMedicineTypeId()).orElseThrow(() -> new IllegalArgumentException("MedicineType not found"));

        MedicineRecord medicineRecord = medicineRecordRepository.findById(
                medicineTypePlanDTO.getMedicinePlanId()).orElseThrow(() -> new IllegalArgumentException("MedicineRecord not found"));
        medicineTypePlan.setMedicinePlanId(medicineRecord);
        medicineTypePlan.setMedicineTypeId(medicineType);
        return medicineTypePlanRepository.save(medicineTypePlan);

    }

    @Override
    public Optional<MedicineTypePlan> getMedicineTypePlanById(Integer id) {
        return medicineTypePlanRepository.findById(id);
    }

    @Override
    public List<MedicineTypePlan> getAllMedicineTypePlans() {
        return medicineTypePlanRepository.findAll();
    }

    @Override
    public MedicineTypePlan updateMedicineTypePlan(MedicineTypePlanDTO medicineTypePlanDTO) {
        MedicineTypePlan medicineTypePlan =  new MedicineTypePlan();
        MedicineType medicineType = medicineTypeRepository.findById(
                medicineTypePlanDTO.getMedicineTypeId()).orElseThrow(() -> new IllegalArgumentException("MentalRule not found"));

        MedicineRecord medicineRecord = medicineRecordRepository.findById(
                medicineTypePlanDTO.getMedicinePlanId()).orElseThrow(() -> new IllegalArgumentException("MentalRecord not found"));
        medicineTypePlan.setId(medicineTypePlanDTO.getId());
        medicineTypePlan.setMedicinePlanId(medicineRecord);
        medicineTypePlan.setMedicineTypeId(medicineType);
        return medicineTypePlanRepository.save(medicineTypePlan);
    }

    @Override
    public void deleteMedicineTypePlan(Integer id) {
        medicineTypePlanRepository.deleteById(id);
    }
}
