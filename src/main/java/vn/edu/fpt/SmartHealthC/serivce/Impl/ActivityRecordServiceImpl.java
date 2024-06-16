package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.ActivityRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.ActivityRecordService;

import java.util.*;

@Service
public class ActivityRecordServiceImpl implements ActivityRecordService {

    @Autowired
    private ActivityRecordRepository activityRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public ActivityRecord createActivityRecord(ActivityRecordDTO activityRecordDTO)
    {
        ActivityRecord activityRecord =  ActivityRecord.builder()
                .planDuration(activityRecordDTO.getPlanDuration())
                .actualDuration(0f)
                .planType(activityRecordDTO.getPlanType())
                .weekStart(activityRecordDTO.getWeekStart())
                .date(activityRecordDTO.getDate()).build();
        Optional<AppUser> appUser = appUserRepository.findById(activityRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        activityRecord.setAppUserId(appUser.get());
        return  activityRecordRepository.save(activityRecord);
    }

    @Override
    public ActivityRecord getActivityRecordById(Integer id) {
        Optional<ActivityRecord> activityRecord = activityRecordRepository.findById(id);
        if(activityRecord.isEmpty()) {
            throw new AppException(ErrorCode.ACTIVITY_RECORD_NOT_FOUND);
        }

        return activityRecord.get();
    }

    @Override
    public List<ActivityRecordResListDTO> getAllActivityRecords(Integer userId) {
        List<Date> activityWeekList = activityRecordRepository.findDistinctWeek(userId);
        List<ActivityRecordResListDTO> responseDTOList = new ArrayList<>();
        for (Date week : activityWeekList) {
            ActivityRecordResListDTO activityRecordResListDTO = ActivityRecordResListDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(activityRecordResListDTO);
        }

        for (ActivityRecordResListDTO record : responseDTOList) {
            List<ActivityRecord> activityRecordList = activityRecordRepository.findByWeekStart(record.getWeekStart(), userId);
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            Float avgDuration = 0f;
            int countDuration = 0;
            for (ActivityRecord activityRecord : activityRecordList) {
                RecordPerDay recordPerDay = RecordPerDay
                        .builder()
                        .date(activityRecord.getDate())
                        .duration(activityRecord.getActualDuration())
                        .build();
                if (activityRecord.getActualType() != null) {
                    recordPerDay.setActivityType(activityRecord.getActualType());
                }
                if (recordPerDay.getDuration() != null) {
                    avgDuration += recordPerDay.getDuration();
                    countDuration++;
                }
                recordPerDayList.add(recordPerDay);
            }
            if (countDuration != 0) {
                avgDuration = avgDuration / countDuration;
                avgDuration = (float) (Math.round(avgDuration * 100) / 100);
            }
            record.setAvgValue(avgDuration);
            record.setRecordPerDayList(recordPerDayList);
        }
        return responseDTOList;
    }

    @Override
    public ActivityRecord updateActivityRecord(Integer id, ActivityRecordDTO activityRecordDTO) {
        ActivityRecord activityRecord = getActivityRecordById(id);
        activityRecord.setDate(activityRecordDTO.getDate());
        activityRecord.setWeekStart(activityRecordDTO.getWeekStart());
        activityRecord.setPlanDuration(activityRecordDTO.getPlanDuration());
        activityRecord.setActualDuration(activityRecordDTO.getActualDuration());
        activityRecord.setPlanType(activityRecordDTO.getPlanType());
        activityRecord.setActualType(activityRecordDTO.getActualType());
        return  activityRecordRepository.save(activityRecord);
    }

    @Override
    public ActivityRecord deleteActivityRecord(Integer id) {
        ActivityRecord activityRecord = getActivityRecordById(id);
        activityRecordRepository.deleteById(id);
        return activityRecord;
    }

}