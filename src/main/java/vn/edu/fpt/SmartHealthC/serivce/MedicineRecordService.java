package vn.edu.fpt.SmartHealthC.serivce;


import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;

import java.util.List;
import java.util.Optional;

public interface MedicineRecordService {
    MedicineRecord createMedicineRecord(MedicineRecordDTO medicineRecordDTO);
    MedicineRecord getMedicineRecordById(Integer id);
    List<MedicineRecord> getAllMedicineRecords();
    MedicineRecord updateMedicineRecord(Integer id,MedicineRecordDTO medicineRecordDTO);
    MedicineRecord deleteMedicineRecord(Integer id);
}
