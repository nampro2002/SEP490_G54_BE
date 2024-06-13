package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.DietRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;

import java.util.*;

@Service
public class DietRecordServiceImpl implements DietRecordService {

    @Autowired
    private DietRecordRepository dietRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public DietRecord createDietRecord(DietRecordDTO dietRecordDTO) {
        DietRecord dietRecord =  DietRecord.builder()
                .dishPerDay(dietRecordDTO.getDishPerDay())
                .weekStart(dietRecordDTO.getWeekStart())
                .actualValue(dietRecordDTO.getActualValue())
                .date(dietRecordDTO.getDate()).build();
        Optional<AppUser> appUser = appUserRepository.findById(dietRecordDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        dietRecord.setAppUserId(appUser.get());
        return  dietRecordRepository.save(dietRecord);
    }

    @Override
    public DietRecord getDietRecordById(Integer id) {
        Optional<DietRecord> dietRecord = dietRecordRepository.findById(id);
        if (dietRecord.isEmpty()) {
            throw new AppException(ErrorCode.DIET_RECORD_NOT_FOUND);
        }
        return dietRecord.get();
    }

    @Override
    public List<DietRecordListResDTO> getAllDietRecords(Integer userId) {
        List<Date> dietWeekList = dietRecordRepository.findDistinctWeek(userId);
        List<DietRecordListResDTO> responseDTOList = new ArrayList<>();
        for (Date week : dietWeekList) {
            DietRecordListResDTO dietRecordListResDTO = DietRecordListResDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(dietRecordListResDTO);
        }

        for (DietRecordListResDTO record : responseDTOList) {
            List<DietRecord> dietRecords = dietRecordRepository.findByWeekStart(record.getWeekStart(), userId);
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            Float avgDish = 0f;
            int count = 0;
            for (DietRecord dietRecord : dietRecords) {
                RecordPerDay recordPerDay = RecordPerDay.builder()
                        .date(dietRecord.getDate())
                        .dishPerDay(dietRecord.getActualValue())
                        .build();
                recordPerDayList.add(recordPerDay);
                //sortby getTimeMeasure getIndex and Date date;
                recordPerDayList.sort(Comparator.comparing(RecordPerDay::getDate));

                if (dietRecord.getDishPerDay() != null) {
                    avgDish += dietRecord.getActualValue() ;
                    count++;
                }
            }
            if (count != 0) {
                avgDish = avgDish / count;
                avgDish = (float) (Math.round(avgDish * 100) / 100);
            }
            record.setAvgValue(avgDish);
            record.setRecordPerDayList(recordPerDayList);
        }

        return responseDTOList;
    }

    @Override
    public DietRecord updateDietRecord(Integer id,DietRecordDTO dietRecordDTO) {
        DietRecord dietRecord = getDietRecordById(id);
        dietRecord.setDishPerDay(dietRecordDTO.getDishPerDay());
        dietRecord.setWeekStart(dietRecordDTO.getWeekStart());
        dietRecord.setActualValue(dietRecordDTO.getActualValue());
        dietRecord.setDate(dietRecordDTO.getDate());
        return  dietRecordRepository.save(dietRecord);
    }

    @Override
    public DietRecord deleteDietRecord(Integer id) {
        DietRecord dietRecord = getDietRecordById(id);
        dietRecordRepository.deleteById(id);
        return dietRecord;
    }
}