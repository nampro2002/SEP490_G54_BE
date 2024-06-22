package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;

import java.text.ParseException;
import java.util.List;

public interface MentalRecordService {
    MentalRecordResponseDTO createMentalRecord(MentalRecordCreateDTO mentalRecordDTO) throws ParseException;
    MentalRecord getMentalRecordEntityById(Integer id);
    MentalRecordResponseDTO getMentalRecordById(Integer id);
    List<MentalRecordListResDTO> getAllMentalRecords(Integer userId);
    MentalRecordResponseDTO updateMentalRecord( MentalRecordUpdateDTO mentalRecordDTO);
    MentalRecordResponseDTO deleteMentalRecord(Integer id);
}