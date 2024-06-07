package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;

import java.util.List;
import java.util.Optional;

public interface MentalRecordService {
    MentalRecord createMentalRecord(MentalRecordDTO mentalRecordDTO);
    Optional<MentalRecord> getMentalRecordById(Integer id);
    List<MentalRecord> getAllMentalRecords();
    MentalRecord updateMentalRecord(MentalRecordDTO mentalRecordDTO);
    void deleteMentalRecord(Integer id);
}