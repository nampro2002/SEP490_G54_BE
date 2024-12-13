package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.DietRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.DietRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.DietRecordService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Transactional
    @Override
    public void createDietRecord(DietRecordCreateDTO dietRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        String weekStartStr= formatDate.format(dietRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<DietRecord> dietPlanExist = dietRecordRepository.findByAppUser(appUser.get().getId());
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
                    .weekStart(DateUtils.normalizeDate(formatDate,weekStartStr))
                    .date(dateCalculate).build();

            dietRecord.setAppUserId(appUser.get());
            dietRecordRepository.save(dietRecord);
            count++;
        }
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
                        .dishPerDay(dietRecord.getActualValue() == null ? 0 : dietRecord.getActualValue())
                        .build();
                recordPerDayList.add(recordPerDay);
                //sortby getTimeMeasure getIndex and Date date;
                recordPerDayList.sort(Comparator.comparing(RecordPerDay::getDate));

                if (dietRecord.getDishPerDay() != null) {
                    avgDish += dietRecord.getActualValue() == null ? 0 : dietRecord.getActualValue();
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

    @Transactional
    @Override
    public void updateDietRecord(DietRecordUpdateDTO dietRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        String dateStr= formatDate.format(dietRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<DietRecord> dietPlanExist = dietRecordRepository.findByAppUser(appUser.get().getId());
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
        dietRecordRepository.save(dietRecordUpdate);
    }

    @Override
    public DietRecord deleteDietRecord(Integer id) {
        DietRecord dietRecord = getDietRecordById(id);
        dietRecordRepository.deleteById(id);
        return dietRecord;
    }

    @Override
    public DietResponseChartDTO getDataChart() throws ParseException {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        List<DietRecord> dietRecordList = dietRecordRepository.find5RecordByIdUser(appUser.getId());
        double sumValue = 0;
        DietResponseChartDTO dietResponseChartDTO = new DietResponseChartDTO();
        List<DietResponse> dietResponseList = new ArrayList<>();
        for (DietRecord dietRecord : dietRecordList) {
                    Integer value = (int) Math.round(dietRecord.getActualValue() == null ? 0 : dietRecord.getActualValue());
                    DietResponse dietResponse = new DietResponse().builder()
                            .date(dietRecord.getDate()).value(value).build();
                    sumValue+=dietRecord.getActualValue() == null ? 0 : dietRecord.getActualValue();
                    dietResponseList.add(dietResponse);
            }
        //sắp xếp tăng dần theo date
        dietResponseList.sort(new Comparator<DietResponse>() {
            @Override
            public int compare(DietResponse recordDateSmaller, DietResponse recordDateBigger) {
                return recordDateSmaller.getDate().compareTo(recordDateBigger.getDate());
            }
        });
        dietResponseChartDTO.setDietResponseList(dietResponseList);
        dietResponseChartDTO.setAvgValue((int) (sumValue/dietResponseChartDTO.getDietResponseList().stream().count()));
        return  dietResponseChartDTO;
    }

    @Override
    public Integer getDishPlan(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);
        Date weekStartNow = formatDate.parse(weekStart);
        System.out.println(weekStartNow);
        List<DietRecord> dietRecordList = dietRecordRepository.findByAppUser(appUser.get().getId());
        Optional<DietRecord> dietRecord = dietRecordList.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(date) && recordWeekStart.equals(weekStartNow);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        if (dietRecord.isEmpty()) {
            throw new AppException(ErrorCode.DIET_DAY_NOT_FOUND);
        }
        return dietRecord.get().getDishPerDay();
    }

    @Override
    public Boolean checkPlanPerDay(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);
        Date weekStartNow = formatDate.parse(weekStart);
        List<DietRecord> dietRecordList = dietRecordRepository.findByAppUser(appUser.get().getId());
        Optional<DietRecord> dietRecord = dietRecordList.stream()
                .filter(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordDate.equals(date)
                                && recordWeekStart.equals(weekStartNow);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        //Hôm nay không có plan tuần
        if (dietRecord.isEmpty()) {
//            throw new AppException(ErrorCode.DIET_PLAN_NOT_FOUND);
        return false;
        }
        // có mà hôm nay chưa nhập
        if (dietRecord.get().getActualValue() == null) {
//            throw new AppException(ErrorCode.DIET_DAY_DATA_EMPTY);
        return false;
        }

        return true;
    }

    @Override
    public Boolean checkPlanExist(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        Date weekStartNow = formatDate.parse(weekStart);
        List<DietRecord> dietRecordList = dietRecordRepository.findByAppUser(appUser.get().getId());
        List<DietRecord> dietRecord = dietRecordList.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartNow);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        //Hôm nay không có plan tuần
        if (dietRecord.isEmpty()) {
            return false;
        }
        return true;
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
}