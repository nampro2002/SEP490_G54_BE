package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.NumeralRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.NumeralRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.NumeralRecordService;

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
                .typeCardinalIndex(numeralRecordDTO.getTypeCardinalIndex())
                .timeMeasure(numeralRecordDTO.getTimeMeasure()).build();
        Optional<AppUser> appUser = appUserRepository.findById(numeralRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        numeralRecord.setAppUserId(appUser.get());
        return  numeralRecordRepository.save(numeralRecord);
    }

    @Override
    public NumeralRecord getNumeralRecordById(Integer id) {
        Optional<NumeralRecord> numeralRecord = numeralRecordRepository.findById(id);
        if(numeralRecord.isEmpty()) {
            throw new AppException(ErrorCode.NUMERAL_NOT_FOUND);
        }
        return numeralRecord.get();
    }

    @Override
    public List<NumeralRecord> getAllNumeralRecords() {
        return numeralRecordRepository.findAll();
    }

    @Override
    public NumeralRecord updateNumeralRecord(Integer id,NumeralRecordDTO numeralRecordDTO) {
        NumeralRecord numeralRecord = getNumeralRecordById(id);
        numeralRecord.setValue(numeralRecordDTO.getValue());
        numeralRecord.setWeekStart(numeralRecordDTO.getWeekStart());
        numeralRecord.setDate(numeralRecordDTO.getDate());
        numeralRecord.setTypeCardinalIndex(numeralRecordDTO.getTypeCardinalIndex());
        numeralRecord.setTimeMeasure(numeralRecordDTO.getTimeMeasure());
        return  numeralRecordRepository.save(numeralRecord);
    }

    @Override
    public NumeralRecord deleteNumeralRecord(Integer id) {
        NumeralRecord numeralRecord = getNumeralRecordById(id);
        numeralRecordRepository.deleteById(id);
        return numeralRecord;
    }


}