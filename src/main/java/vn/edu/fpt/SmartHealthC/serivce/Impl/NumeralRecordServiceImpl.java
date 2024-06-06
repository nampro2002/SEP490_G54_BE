package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.NumeralRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.NumeralRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.NumeralRecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class NumeralRecordServiceImpl implements NumeralRecordService {

    @Autowired
    private NumeralRecordRepository numeralRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public NumeralRecord createNumeralRecord(NumeralRecordDTO numeralRecordDTO)
    {
        NumeralRecord numeralRecord =  NumeralRecord.builder()
                .value(numeralRecordDTO.getValue())
                .weekStart(numeralRecordDTO.getWeekStart())
                .date(numeralRecordDTO.getDate())
                .typeNumeral(numeralRecordDTO.getTypeNumeral())
                .timeMeasure(numeralRecordDTO.getTimeMeasure()).build();
        AppUser appUser = appUserRepository.findById(numeralRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        numeralRecord.setAppUserId(appUser);
        return  numeralRecordRepository.save(numeralRecord);
    }

    @Override
    public Optional<NumeralRecord> getNumeralRecordById(Integer id) {
        return numeralRecordRepository.findById(id);
    }

    @Override
    public List<NumeralRecord> getAllNumeralRecords() {
        return numeralRecordRepository.findAll();
    }

    @Override
    public NumeralRecord updateNumeralRecord(NumeralRecordDTO numeralRecordDTO) {
        NumeralRecord numeralRecord =  NumeralRecord.builder()
                .Id(numeralRecordDTO.getId())
                .value(numeralRecordDTO.getValue())
                .weekStart(numeralRecordDTO.getWeekStart())
                .date(numeralRecordDTO.getDate())
                .typeNumeral(numeralRecordDTO.getTypeNumeral())
                .timeMeasure(numeralRecordDTO.getTimeMeasure()).build();
        AppUser appUser = appUserRepository.findById(numeralRecordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        numeralRecord.setAppUserId(appUser);
        return  numeralRecordRepository.save(numeralRecord);
    }

    @Override
    public void deleteNumeralRecord(Integer id) {
        numeralRecordRepository.deleteById(id);
    }


}