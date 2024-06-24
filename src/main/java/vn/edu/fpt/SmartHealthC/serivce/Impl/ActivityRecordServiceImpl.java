package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordUpdateDTO;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    @Autowired
    private SimpleDateFormat formatDate;

    @Override
    public ActivityRecord createActivityRecord(ActivityRecordCreateDTO activityRecordDTO) throws ParseException {
        activityRecordDTO.getWeekStart();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);

        String weekStartStr= formatDate.format(activityRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<ActivityRecord> planExist = activityRecordRepository.findRecordByIdUser(appUser.getId());
        boolean dateExists = planExist.stream()
                .anyMatch(record -> {
                    String recordDateStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(weekStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                });
        if (dateExists) {
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
    public Date calculateDate(Date sourceDate , int plus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, plus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
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
    public ActivityRecord updateActivityRecord( ActivityRecordUpdateDTO activityRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);

        String dateStr= formatDate.format(activityRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<ActivityRecord> planExist = activityRecordRepository.findRecordByIdUser(appUser.getId());
        Optional<ActivityRecord> activityRecord = planExist.stream()
                .filter(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(date);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        if (activityRecord.isEmpty()) {
            throw new AppException(ErrorCode.ACTIVITY_DAY_NOT_FOUND);
        }

        ActivityRecord activityRecordUpdate =getActivityRecordById(activityRecord.get().getId());
        activityRecordUpdate.setDate(activityRecordDTO.getDate());
        activityRecordUpdate.setActualDuration(activityRecordDTO.getActualDuration());
        activityRecordUpdate.setActualType(activityRecordDTO.getActualType());
        return  activityRecordRepository.save(activityRecordUpdate);
    }

    @Override
    public ActivityRecord deleteActivityRecord(Integer id) {
        ActivityRecord activityRecord = getActivityRecordById(id);
        activityRecordRepository.deleteById(id);
        return activityRecord;
    }

}