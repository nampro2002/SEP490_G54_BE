package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ActivityRecordServiceImpl implements ActivityRecordService {

    @Autowired
    private ActivityRecordRepository activityRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;

    @Override
    public ActivityRecord createActivityRecord(ActivityRecordDTO activityRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);

        List<ActivityRecord> planExist = activityRecordRepository.findByWeekStart(activityRecordDTO.getWeekStart(),
                appUser.getId());
        if(!planExist.isEmpty()){
            throw new AppException(ErrorCode.ACTIVITY_PLAN_EXIST);
        }

        Date dateCalculate;
        Map<Integer, String> dayIndexMap = new HashMap<>();
        dayIndexMap.put(0, "MONDAY");
        dayIndexMap.put(1, "TUESDAY");
        dayIndexMap.put(2, "WEDNESDAY");
        dayIndexMap.put(3, "THURSDAY");
        dayIndexMap.put(4, "FRIDAY");
        dayIndexMap.put(5, "SATURDAY");
        dayIndexMap.put(6, "SUNDAY");
        int count = 0;
        //Check schedule
        while(true){
            if(count >= 7 ){
                break;
            }
            ActivityRecord activityRecord =  ActivityRecord.builder()
                    .actualDuration(0f)
                    .weekStart(activityRecordDTO.getWeekStart()).build();
            activityRecord.setAppUserId(appUser);
            activityRecord.setPlanType(activityRecordDTO.getPlanType());
            if (activityRecordDTO.getSchedule().contains(dayIndexMap.get(count))) {
                activityRecord.setPlanDuration(activityRecordDTO.getPlanDuration());
            }else{
                activityRecord.setPlanDuration(0f);
            }
            dateCalculate = calculateDate(activityRecordDTO.getWeekStart(), count);
            activityRecord.setDate(dateCalculate);
            activityRecordRepository.save(activityRecord);
            count++;
        }
        return null;
    }
    public Date calculateDate(Date date , int plus) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate firstWeekStart = LocalDate.parse(formatDate.format(date), formatter);
        firstWeekStart = firstWeekStart.plusDays(plus);

        String formattedDate = firstWeekStart.format(formatter);
        return formatDate.parse(formattedDate);
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