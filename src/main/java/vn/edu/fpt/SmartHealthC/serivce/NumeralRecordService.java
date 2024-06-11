package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.NMRDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;

import java.util.List;

public interface NumeralRecordService {
    CardinalRecord createNumeralRecord(NumeralRecordDTO numeralRecordDTO);

    CardinalRecord getNumeralRecordById(Integer id);

    List<CardinalRecordResponseDTO> getAllNumeralRecords(Integer userId);

    CardinalRecord updateNumeralRecord(Integer id, NumeralRecordDTO numeralRecordDTO);

    CardinalRecord deleteNumeralRecord(Integer id);

    List<CardinalRecordResponseDTO> getAllCardinalRecordsByAppUserId(Integer id);

    List<CardinalRecord> getAllNumeralRecordsVip();
}