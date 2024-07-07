package vn.edu.fpt.SmartHealthC.serivce;


import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicinePLanResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicinePlanPerDayResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicineResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;

import java.text.ParseException;
import java.util.List;

public interface MedicineRecordService {
    void createMedicineRecord(List<MedicineRecordCreateDTO> medicineRecordDTO) throws ParseException;
    MedicineRecordResponseDTO getMedicineRecordById(Integer id);
    MedicineRecord getMedicineRecordEntityById(Integer id);
    List<MedicineRecordListResDTO> getAllMedicineRecords(Integer userId);
    void updateMedicineRecord(MedicineRecordUpdateDTO medicineRecordDTO) throws ParseException;
    MedicineRecordResponseDTO deleteMedicineRecord(Integer id);
    List<MedicinePLanResponseDTO> getAllMedicinePlans(String weekStart) throws ParseException;

    MedicineResponseChartDTO getDataChart() throws ParseException;

    List<MedicinePlanPerDayResponse> getMedicinePerDay(String weekStart) throws ParseException;

    Boolean checkPlanPerDay(String weekStart) throws ParseException;

    Boolean checkPlanExist(String weekStart) throws ParseException;
}
