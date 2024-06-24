package vn.edu.fpt.SmartHealthC.serivce;


import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;

import java.text.ParseException;
import java.util.List;

public interface MedicineRecordService {
    MedicineRecordResponseDTO createMedicineRecord(MedicineRecordCreateDTO medicineRecordDTO) throws ParseException;
    MedicineRecordResponseDTO getMedicineRecordById(Integer id);
    MedicineRecord getMedicineRecordEntityById(Integer id);
    List<MedicineRecordListResDTO> getAllMedicineRecords(Integer userId);
    MedicineRecordResponseDTO updateMedicineRecord(MedicineRecordUpdateDTO medicineRecordDTO) throws ParseException;
    MedicineRecordResponseDTO deleteMedicineRecord(Integer id);
}
