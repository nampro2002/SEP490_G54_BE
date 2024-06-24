package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;

import java.text.ParseException;
import java.util.List;

public interface CardinalRecordService {
    CardinalRecord createCardinalRecord(CardinalRecordDTO cardinalRecordDTO) throws ParseException;

    CardinalRecord getCardinalRecordById(Integer id);

    List<CardinalRecordResponseDTO> getAllCardinalRecords(Integer userId);

    CardinalRecord updateCardinalRecord(Integer id, CardinalRecordDTO cardinalRecordDTO);

    CardinalRecord deleteCardinalRecord(Integer id);

    List<CardinalRecordResponseDTO> getAllCardinalRecordsByAppUserId(Integer id);

    List<CardinalRecord> getAllCardinalRecordsVip();
}