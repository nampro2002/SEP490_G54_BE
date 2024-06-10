package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.NumeralRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.NumeralRecord;

import java.util.List;
import java.util.Optional;

public interface NumeralRecordService {
    NumeralRecord createNumeralRecord(NumeralRecordDTO numeralRecordDTO);
   NumeralRecord getNumeralRecordById(Integer id);
    List<NumeralRecord> getAllNumeralRecords();
    NumeralRecord updateNumeralRecord(Integer id,NumeralRecordDTO numeralRecordDTO);
    NumeralRecord deleteNumeralRecord(Integer id);
}