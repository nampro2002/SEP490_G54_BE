package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO.ActivityResponseChartDTO;
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
    public void createActivityRecord(ActivityRecordCreateDTO activityRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        String weekStartStr= formatDate.format(activityRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<ActivityRecord> planExist = activityRecordRepository.findRecordByIdUser(appUser.get().getId());
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
            activityRecord.setAppUserId(appUser.get());
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
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        String dateStr= formatDate.format(activityRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<ActivityRecord> planExist = activityRecordRepository.findRecordByIdUser(appUser.get().getId());
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
    public Date calculateDateMinus(Date sourceDate , int minus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, -minus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }

    @Override
    public ActivityResponseChartDTO getDataChart() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);

        List<ActivityRecord> activityRecordList = activityRecordRepository.findRecordByIdUser(appUser.get().getId());
        //Sắp xếp giảm dần theo date
        activityRecordList.sort(new Comparator<ActivityRecord>() {
            @Override
            public int compare(ActivityRecord recordDateSmaller, ActivityRecord recordDateBigger) {
                return recordDateBigger.getDate().compareTo(recordDateSmaller.getDate());
            }
        });
        int count = 5;
        ActivityResponseChartDTO activityResponseChartDTO = new ActivityResponseChartDTO();
        List<ActivityResponse> activityResponseList = new ArrayList<>();
        for (ActivityRecord activityRecord : activityRecordList) {
            String smallerDateStr= formatDate.format(activityRecord.getDate());
            Date smallerDate = formatDate.parse(smallerDateStr);

            if(smallerDate.before(date)){
                Integer value = (int) Math.round(activityRecord.getActualDuration());
                ActivityResponse activityResponse = new ActivityResponse()
                        .builder().date(activityRecord.getDate())
                        .duration(value)
                        .type(activityRecord.getActualType()).build();
                count--;
                activityResponseList.add(activityResponse);
            }
            if(smallerDate.equals(date)){
                Integer value = (int) Math.round(activityRecord.getActualDuration());
                activityResponseChartDTO.setDurationToday(value);
                activityResponseChartDTO.setTypeToDay(activityRecord.getActualType());
                ActivityResponse activityResponse = new ActivityResponse()
                        .builder().date(activityRecord.getDate())
                        .duration(value)
                        .type(activityRecord.getActualType()).build();
                activityResponseList.add(activityResponse);
                count--;
            }
            today = calculateDateMinus(today,1);
            if(count < 1){
                break;
            }
        }
        //sắp xếp tăng dần theo date
        activityResponseList.sort(new Comparator<ActivityResponse>() {
            @Override
            public int compare(ActivityResponse recordDateSmaller, ActivityResponse recordDateBigger) {
                return recordDateSmaller.getDate().compareTo(recordDateBigger.getDate());
            }
        });
        activityResponseChartDTO.setActivityResponseList(activityResponseList);

        return activityResponseChartDTO;
    }


}