package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;

import java.util.List;

public interface BloodPressureRecordService {
    BloodPressureRecord createBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO);
    BloodPressureRecord getBloodPressureRecordById(Integer id);
    List<BloodPressureRecord> getAllBloodPressureRecords();
    List<BloodPressureResponseDTO> getListBloodPressureRecordsByUser(Integer id);
    BloodPressureRecord updateBloodPressureRecord( Integer id,BloodPressureRecordDTO bloodPressureRecordDTO);
    BloodPressureRecord deleteBloodPressureRecord(Integer id);
}