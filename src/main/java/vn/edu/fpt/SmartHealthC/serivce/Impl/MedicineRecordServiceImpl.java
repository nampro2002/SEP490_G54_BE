package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;

import java.util.List;
import java.util.Optional;

public class MedicineRecordServiceImpl implements MedicineRecordService {
    @Autowired
    private MedicineRecordRepository medicineRecordRepository;

    @Override
    public MedicineRecord createMedicineRecord(MedicineRecord medicineRecord) {
        return medicineRecordRepository.save(medicineRecord);
    }

    @Override
    public Optional<MedicineRecord> getMedicineRecordById(Integer id) {
        return medicineRecordRepository.findById(id);
    }

    @Override
    public List<MedicineRecord> getAllMedicineRecords() {
        return medicineRecordRepository.findAll();
    }

    @Override
    public MedicineRecord updateMedicineRecord(MedicineRecord medicineRecord) {
        return medicineRecordRepository.save(medicineRecord);
    }

    @Override
    public void deleteMedicineRecord(Integer id) {
        medicineRecordRepository.deleteById(id);
    }
}
