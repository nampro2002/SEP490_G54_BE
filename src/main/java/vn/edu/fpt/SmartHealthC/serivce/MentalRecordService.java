package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;

import java.util.List;
import java.util.Optional;

public interface MentalRecordService {
    MentalRecord createMentalRecord(MentalRecord mentalRecord);
    Optional<MentalRecord> getMentalRecordById(Integer id);
    List<MentalRecord> getAllMentalRecords();
    MentalRecord updateMentalRecord(MentalRecord mentalRecord);
    void deleteMentalRecord(Integer id);
}