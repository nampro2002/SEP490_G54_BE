package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;

import java.text.ParseException;
import java.util.List;

public interface MentalRecordService {
    void createMentalRecord(MentalRecordCreateDTO mentalRecordDTO) throws ParseException;
    MentalRecord getMentalRecordEntityById(Integer id);
    MentalRecordResponseDTO getMentalRecordById(Integer id);
    List<MentalRecordListResDTO> getAllMentalRecords(Integer userId);
    MentalRecordResponseDTO updateMentalRecord( MentalRecordUpdateDTO mentalRecordDTO) throws ParseException;
    MentalRecordResponseDTO deleteMentalRecord(Integer id);

    MentalResponseChartDTO getDataChart() throws ParseException;

    List<MentalRule> getListMentalPerWeek(String weekStart) throws ParseException;
}