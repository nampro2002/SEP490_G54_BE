package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;

import java.util.List;

public interface MentalRecordService {
    MentalRecordResponseDTO createMentalRecord(MentalRecordDTO mentalRecordDTO);
    MentalRecord getMentalRecordEntityById(Integer id);
    MentalRecordResponseDTO getMentalRecordById(Integer id);
    List<MentalRecordListResDTO> getAllMentalRecords(Integer userId);
    MentalRecordResponseDTO updateMentalRecord(Integer id,MentalRecordDTO mentalRecordDTO);
    MentalRecordResponseDTO deleteMentalRecord(Integer id);
}