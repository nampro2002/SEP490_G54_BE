package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;

import java.util.List;
import java.util.Optional;

public interface MedicineRecordService {
    MedicineRecord createMedicineRecord(MedicineRecord medicineRecord);
    Optional<MedicineRecord> getMedicineRecordById(Integer id);
    List<MedicineRecord> getAllMedicineRecords();
    MedicineRecord updateMedicineRecord(MedicineRecord medicineRecord);
    void deleteMedicineRecord(Integer id);
}
