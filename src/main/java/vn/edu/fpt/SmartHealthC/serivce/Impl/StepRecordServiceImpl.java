package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicineResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicineResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponse;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.StepRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StepRecordServiceImpl implements StepRecordService {

    @Autowired
    private StepRecordRepository stepRecordRepository;
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
    public void createStepRecord(StepRecordCreateDTO stepRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);


        String weekStartStr= formatDate.format(stepRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<StepRecord> stepPlanExist = stepRecordRepository.findByAppUserId(appUser.get().getId());
        boolean dateExists = stepPlanExist.stream()
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
            throw new AppException(ErrorCode.STEP_PLAN_EXIST);
        }

        int count=0;
        Date dateCalculate;
        while(true){
            if(count >= 7 ){
                break;
            }
            dateCalculate = calculateDate(stepRecordDTO.getWeekStart(), count);
            StepRecord stepRecord = StepRecord.builder()
                    .plannedStepPerDay(stepRecordDTO.getPlannedStepPerDay())
                    .actualValue(0f)
                    .weekStart(stepRecordDTO.getWeekStart())
                    .date(dateCalculate).build();
            stepRecord.setAppUserId(appUser.get());
            stepRecordRepository.save(stepRecord);
            count++;
        }
    }

    @Override
    public StepRecord getStepRecordById(Integer id) {
        Optional<StepRecord> stepRecord = stepRecordRepository.findById(id);
        if (stepRecord.isEmpty()) {
            throw new AppException(ErrorCode.STEP_RECORD_NOT_FOUND);
        }
        return stepRecord.get();
    }

    @Override
    public List<StepRecordResListDTO> getAllStepRecords(Integer userId) {
        List<Date> recordDate = stepRecordRepository.findDistinctWeek(userId);
        List<StepRecordResListDTO> listResponseDTOList = new ArrayList<>();

        for (Date date : recordDate) {
            listResponseDTOList.add(StepRecordResListDTO.builder()
                    .appUserId(userId)
                    .weekStart(date)
                    .build());
        }
        for (StepRecordResListDTO record : listResponseDTOList) {
            Float avgStep = 0f;
            int countStep = 0;
            List<StepRecord> stepRecordList = stepRecordRepository.findByAppUserIdAndWeekStart(userId, record.getWeekStart());
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            for (StepRecord stepRecord : stepRecordList) {
                RecordPerDay recordPerDay = RecordPerDay.builder()
                        .step(stepRecord.getActualValue())
                        .date(stepRecord.getDate())
                        .build();
                recordPerDayList.add(recordPerDay);
                if (stepRecord.getActualValue() != null) {
                    avgStep += stepRecord.getActualValue();
                    countStep++;
                }
            }
            if (countStep != 0) {
                avgStep = avgStep / countStep;
                avgStep = (float) (Math.round(avgStep * 100) / 100);
            }
            record.setAvgValue(avgStep);
            record.setRecordPerDayList(recordPerDayList);
        }
        return listResponseDTOList;
    }

    @Override
    public StepRecord updateStepRecord(StepRecordUpdateDTO stepRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);


        String dateStr= formatDate.format(stepRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<StepRecord> stepPlanExist = stepRecordRepository.findByAppUserId(appUser.getId());
        Optional<StepRecord> stepRecord = stepPlanExist.stream()
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
        if (stepRecord.isEmpty()) {
            throw new AppException(ErrorCode.STEP_DAY_NOT_FOUND);
        }

        StepRecord stepRecordUpdate = getStepRecordById(stepRecord.get().getId());
        stepRecordUpdate.setActualValue(stepRecordDTO.getActualValue());
        stepRecordUpdate.setDate(stepRecordDTO.getDate());
        stepRecordRepository.save(stepRecordUpdate);
        return null;
    }

    @Override
    public StepRecord deleteStepRecord(Integer id) {
        StepRecord stepRecord = getStepRecordById(id);
        stepRecordRepository.deleteById(id);
        return stepRecord;
    }

    @Override
    public StepResponseChartDTO getDataChart() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);

        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);


        List<StepRecord> stepRecordList = stepRecordRepository.findByAppUserId(appUser.get().getId());
        //Sắp xếp giảm dần theo date
        stepRecordList.sort(new Comparator<StepRecord>() {
            @Override
            public int compare(StepRecord recordDateSmaller, StepRecord recordDateBigger) {
                return recordDateBigger.getDate().compareTo(recordDateSmaller.getDate());
            }
        });

        StepResponseChartDTO stepResponseChartDTO = new StepResponseChartDTO();
        List<StepResponse> stepResponseList = new ArrayList<>();
        int count = 5;
        for (StepRecord stepRecord : stepRecordList) {
            String smallerDateStr= formatDate.format(stepRecord.getDate());
            Date smallerDate = formatDate.parse(smallerDateStr);

            if(smallerDate.before(date)){

                double valuePercent = ((double) stepRecord.getActualValue() / stepRecord.getPlannedStepPerDay()) * 100;
                StepResponse stepResponse = new StepResponse();
                stepResponse.setValuePercent((int) valuePercent);
                stepResponse.setDate(stepRecord.getDate());
                stepResponseList.add(stepResponse);
                count--;

            }
            if(smallerDate.equals(date)){
                double valuePercent = ((double) stepRecord.getActualValue() / stepRecord.getPlannedStepPerDay()) * 100;
                StepResponse stepResponse = new StepResponse();
                stepResponse.setValuePercent((int) valuePercent);
                stepResponse.setDate(stepRecord.getDate());
                stepResponseList.add(stepResponse);
                Integer value = (int) Math.round(stepRecord.getActualValue());
                stepResponseChartDTO.setValueToday(value);
                count--;
            }
            today = calculateDate(today,1);
            if(count < 1){
                break;
            }
        }
        //Sắp xếp giảm dần theo date
        stepResponseList.sort(new Comparator<StepResponse>() {
            @Override
            public int compare(StepResponse recordDateSmaller, StepResponse recordDateBigger) {
                return recordDateSmaller.getDate().compareTo(recordDateBigger.getDate());
            }
        });


        stepResponseChartDTO.setStepResponseList(stepResponseList);
            return stepResponseChartDTO;
    }


}