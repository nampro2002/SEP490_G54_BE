package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.DietRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.DietRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DietRecordServiceImpl implements DietRecordService {

    @Autowired
    private DietRecordRepository dietRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SimpleDateFormat formatDate;


    public Date calculateDate(Date date , int plus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, plus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }

    @Override
    public DietRecord createDietRecord(DietRecordCreateDTO dietRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);


        String weekStartStr= formatDate.format(dietRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<DietRecord> dietPlanExist = dietRecordRepository.findByAppUser(appUser.getId());
        boolean dateExists = dietPlanExist.stream()
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
            throw new AppException(ErrorCode.DIET_PLAN_EXIST);
        }


        int count = 0;
        Date dateCalculate;
        //Check schedule
        while(true){
            if(count >= 7 ){
                break;
            }
            dateCalculate = calculateDate(dietRecordDTO.getWeekStart(), count);
            DietRecord dietRecord =  DietRecord.builder()
                    .dishPerDay(dietRecordDTO.getDishPerDay())
                    .actualValue(0.f)
                    .weekStart(dietRecordDTO.getWeekStart())
                    .date(dateCalculate).build();

            dietRecord.setAppUserId(appUser);
            dietRecordRepository.save(dietRecord);
            count++;
        }
        return null;
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
    public DietRecord updateDietRecord(DietRecordUpdateDTO dietRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);

        String dateStr= formatDate.format(dietRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<DietRecord> dietPlanExist = dietRecordRepository.findByAppUser(appUser.getId());
        Optional<DietRecord> dietRecord = dietPlanExist.stream()
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
        if (dietRecord.isEmpty()) {
            throw new AppException(ErrorCode.DIET_DAY_NOT_FOUND);
        }


        DietRecord dietRecordUpdate =getDietRecordById(dietRecord.get().getId());
        dietRecordUpdate.setDate(dietRecordDTO.getDate());
        dietRecordUpdate.setActualValue(dietRecordDTO.getActualValue());
        return dietRecordRepository.save(dietRecordUpdate);
    }

    @Override
    public DietRecord deleteDietRecord(Integer id) {
        DietRecord dietRecord = getDietRecordById(id);
        dietRecordRepository.deleteById(id);
        return dietRecord;
    }
}