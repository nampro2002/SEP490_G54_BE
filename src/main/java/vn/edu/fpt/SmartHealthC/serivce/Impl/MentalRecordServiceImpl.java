package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class MentalRecordServiceImpl implements MentalRecordService {

    @Autowired
    private MentalRecordRepository mentalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public MentalRecord createMentalRecord(MentalRecordDTO mentalRecordDTO) {
        MentalRecord mentalRecord =  MentalRecord.builder()
                .point(mentalRecordDTO.getPoint())
                .weekStart(mentalRecordDTO.getWeekStart())
                .date(mentalRecordDTO.getDate())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(mentalRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        mentalRecord.setAppUserId(appUser.get());
        return mentalRecordRepository.save(mentalRecord);
    }

    @Override
    public Optional<MentalRecord> getMentalRecordById(Integer id) {
        return mentalRecordRepository.findById(id);
    }

    @Override
    public List<MentalRecord> getAllMentalRecords() {
        return mentalRecordRepository.findAll();
    }

    @Override
    public MentalRecord updateMentalRecord(MentalRecordDTO mentalRecordDTO) {
        MentalRecord mentalRecord =  MentalRecord.builder()
                .id(mentalRecordDTO.getId())
                .point(mentalRecordDTO.getPoint())
                .weekStart(mentalRecordDTO.getWeekStart())
                .date(mentalRecordDTO.getDate())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(mentalRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        mentalRecord.setAppUserId(appUser.get());
        return mentalRecordRepository.save(mentalRecord);
    }

    @Override
    public void deleteMentalRecord(Integer id) {
        mentalRecordRepository.deleteById(id);
    }
}