package vn.edu.fpt.SmartHealthC.serivce;


import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;

import java.util.List;
import java.util.Optional;

public interface MedicineRecordService {
    MedicineRecordResponseDTO createMedicineRecord(MedicineRecordDTO medicineRecordDTO);
    MedicineRecordResponseDTO getMedicineRecordById(Integer id);
    MedicineRecord getMedicineRecordEntityById(Integer id);
    List<MedicineRecordListResDTO> getAllMedicineRecords(Integer userId);
    MedicineRecordResponseDTO updateMedicineRecord(Integer id,MedicineRecordDTO medicineRecordDTO);
    MedicineRecordResponseDTO deleteMedicineRecord(Integer id);
}
