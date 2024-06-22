package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
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

    public Date calculateDate(Date date , int plus) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        LocalDate firstWeekStart = LocalDate.parse(formatDate.format(date), formatter);
        firstWeekStart = firstWeekStart.plusDays(plus);

        String formattedDate = firstWeekStart.format(formatter);
        return formatDate.parse(formattedDate);
    }

    @Override
    public StepRecord createStepRecord(StepRecordCreateDTO stepRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);

        List<StepRecord> stepPlanExist = stepRecordRepository.findByAppUserIdAndWeekStart(
                appUser.getId(),stepRecordDTO.getWeekStart()
        );
        if(!stepPlanExist.isEmpty()){
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
            stepRecord.setAppUserId(appUser);
            stepRecordRepository.save(stepRecord);
            count++;
        }
        return null;
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
    public StepRecord updateStepRecord(StepRecordUpdateDTO stepRecordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);

        Optional<StepRecord> stepPlanExist = stepRecordRepository.findByAppUserIdAndDate(
                appUser.getId(),stepRecordDTO.getDate()
        );
        if(stepPlanExist.isEmpty()){
            throw new AppException(ErrorCode.STEP_DAY_NOT_FOUND);
        }
        StepRecord stepRecordUpdate = getStepRecordById(stepPlanExist.get().getId());
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


}