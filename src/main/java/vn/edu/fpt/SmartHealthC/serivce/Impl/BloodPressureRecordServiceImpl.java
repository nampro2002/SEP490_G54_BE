package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.BloodPressureRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;

import java.util.*;

@Service
public class BloodPressureRecordServiceImpl implements BloodPressureRecordService {

    @Autowired
    private BloodPressureRecordRepository bloodPressureRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public BloodPressureRecord createBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO) {
        BloodPressureRecord bloodPressureRecord = BloodPressureRecord.builder()
                .diastole(bloodPressureRecordDTO.getDiastole())
                .systole(bloodPressureRecordDTO.getSystole())
                .weekStart(bloodPressureRecordDTO.getWeekStart())
                .date(bloodPressureRecordDTO.getDate()).build();
        Optional<AppUser> appUser = appUserRepository.findById(bloodPressureRecordDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        bloodPressureRecord.setAppUserId(appUser.get());
        return bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public BloodPressureRecord getBloodPressureRecordById(Integer id) {
        Optional<BloodPressureRecord> bloodPressureRecord = bloodPressureRecordRepository.findById(id);
        if (bloodPressureRecord.isEmpty()) {
            throw new AppException(ErrorCode.BLOOD_PRESSURE_NOT_FOUND);
        }
        return bloodPressureRecord.get();
    }

    @Override
    public List<BloodPressureRecord> getAllBloodPressureRecords() {
        return bloodPressureRecordRepository.findAll();
    }

    @Override
    public List<BloodPressureResponseDTO> getListBloodPressureRecordsByUser(Integer userId) {
        List<Date> bloodPressureWeekList = bloodPressureRecordRepository.findDistinctWeek(userId);
        List<BloodPressureResponseDTO> responseDTOList = new ArrayList<>();
        for (Date week : bloodPressureWeekList) {
            BloodPressureResponseDTO bloodPressureResponseDTO = BloodPressureResponseDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(bloodPressureResponseDTO);
        }

        for (BloodPressureResponseDTO record : responseDTOList) {
            List<BloodPressureRecord> bloodPressureRecords = bloodPressureRecordRepository.findByWeekStart(record.getWeekStart(), userId);
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            Float systole = 0f;
            Float diastole = 0f;
            int countSystole = 0;
            int countDiastole = 0;
            for (BloodPressureRecord bloodPressureRecord : bloodPressureRecords) {
                RecordPerDay recordPerDay = RecordPerDay.builder()
                        .date(bloodPressureRecord.getDate())
                        .systole(bloodPressureRecord.getSystole()+ "mmHG")
                        .diastole(bloodPressureRecord.getDiastole()+ "mmHG")
                        .build();
                recordPerDayList.add(recordPerDay);
                //sort by  Date date;
                recordPerDayList.sort(Comparator.comparing(RecordPerDay::getDate));
                if (bloodPressureRecord.getSystole() != null) {
                    systole += bloodPressureRecord.getSystole();
                    countSystole++;
                }
                if (bloodPressureRecord.getDiastole() != null) {
                    diastole += bloodPressureRecord.getDiastole();
                    countDiastole++;
                }
            }
            if (countSystole != 0) {
                systole = systole / countSystole;
                systole = (float) (Math.round(systole * 100) / 100);
            }
            if (countDiastole != 0) {
                diastole = diastole / countDiastole;
                diastole = (float) (Math.round(diastole * 100) / 100);
            }
            record.setAvgValue(systole + "mmHG" + " / " + diastole + "mmHG");
            record.setRecordPerDayList(recordPerDayList);
        }
        return responseDTOList;
    }

    @Override
    public BloodPressureRecord updateBloodPressureRecord(Integer id, BloodPressureRecordDTO bloodPressureRecordDTO) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(id);
        bloodPressureRecord.setDiastole(bloodPressureRecordDTO.getDiastole());
        bloodPressureRecord.setSystole(bloodPressureRecordDTO.getSystole());
        bloodPressureRecord.setWeekStart(bloodPressureRecordDTO.getWeekStart());
        bloodPressureRecord.setDate(bloodPressureRecordDTO.getDate());
        return bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public BloodPressureRecord deleteBloodPressureRecord(Integer id) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(id);
        bloodPressureRecordRepository.deleteById(id);
        return bloodPressureRecord;
    }


}