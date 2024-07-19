package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateContinuousDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.StepRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CurrentStepRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepRecordResListDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO.StepResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.StepRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.WeightRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
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
    @Autowired
    private WeightRecordRepository weightRecordRepository;

    public Date calculateDate(Date date, int plus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, plus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }

    // check bản ghi cùng weekstart đã toonf tại
    // update planRecord vaof banr ghi trrớc đos
    @Transactional
    @Override
    public void createStepRecord(StepRecordCreateDTO stepRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }


        String weekStartStr = formatDate.format(stepRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<StepRecord> recordList = stepRecordRepository.findByAppUserIdAndWeekStart(appUser.get().getId(), weekStart);

        if (recordList.size() == 7) {
            throw new AppException(ErrorCode.STEP_PLAN_EXIST);
        }
        int count = 0;
        Date dateCalculate;
        while (count < 7) {
            dateCalculate = calculateDate(stepRecordDTO.getWeekStart(), count);
            String dateCalculateStr = formatDate.format(dateCalculate);
            Optional<StepRecord> stepRecordOptional = stepRecordRepository.findByAppUserIdAndDate(appUser.get().getId(), dateCalculate);
            if (!stepRecordOptional.isPresent()) {
                StepRecord stepRecord = StepRecord.builder()
                        .plannedStepPerDay(stepRecordDTO.getPlannedStepPerDay())
                        .actualValue(0f)
                        .weekStart(DateUtils.normalizeDate(formatDate, weekStartStr))
                        .date(DateUtils.normalizeDate(formatDate, dateCalculateStr)).build();
                stepRecord.setAppUserId(appUser.get());
                stepRecordRepository.save(stepRecord);
            }
            count++;
        }
        recordList = stepRecordRepository.findByAppUserIdAndWeekStart(appUser.get().getId(), weekStart);
        for (StepRecord record : recordList) {
            if (record.getPlannedStepPerDay() != stepRecordDTO.getPlannedStepPerDay()) {
                record.setPlannedStepPerDay(stepRecordDTO.getPlannedStepPerDay());
                stepRecordRepository.save(record);
            }
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

    // nếu chưa có plan thì tạo record với planStepPerday = 0 và weekstart hiện tại
    @Transactional
    @Override
    public void updateStepRecord(StepRecordUpdateDTO stepRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        String dateStr = formatDate.format(stepRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<StepRecord> stepPlanExist = stepRecordRepository.findByAppUserId(appUser.get().getId());
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
        stepRecordUpdate.setDate(DateUtils.normalizeDate(formatDate, formatDate.format(stepRecordDTO.getDate())));
        stepRecordRepository.save(stepRecordUpdate);
    }

    @Override
    public StepRecord deleteStepRecord(Integer id) {
        StepRecord stepRecord = getStepRecordById(id);
        stepRecordRepository.deleteById(id);
        return stepRecord;
    }

    @Override
    public StepResponseChartDTO getDataChart() throws ParseException {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Date date = DateUtils.getToday(formatDate);
        List<StepRecord> stepRecordList = stepRecordRepository.find5RecordByIdUser(appUser.getId());

        StepResponseChartDTO stepResponseChartDTO = new StepResponseChartDTO();
        List<StepResponse> stepResponseList = new ArrayList<>();
        for (StepRecord stepRecord : stepRecordList) {
            double valuePercent = stepRecord.getPlannedStepPerDay() != 0 ? ((double) stepRecord.getActualValue() / stepRecord.getPlannedStepPerDay()) * 100 : 100;
            StepResponse stepResponse = new StepResponse();
            stepResponse.setValuePercent((int) valuePercent);
            stepResponse.setDate(stepRecord.getDate());
            stepResponseList.add(stepResponse);
        }
        String todayStr = formatDate.format(stepResponseList.get(0).getDate());
        Date todayDate = formatDate.parse(todayStr);
        if (date.equals(todayDate)) {
            Integer value = (int) Math.round(stepRecordList.get(0).getActualValue());
            stepResponseChartDTO.setValueToday(value);
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

    @Override
    public Boolean checkPlanPerDay(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date today = new Date();
        String dateStr = formatDate.format(today);
        Date date = formatDate.parse(dateStr);
        Date weekStartNow = formatDate.parse(weekStart);
        List<StepRecord> stepRecordsList = stepRecordRepository.findByAppUserId(appUser.get().getId());
        Optional<StepRecord> stepRecords = stepRecordsList.stream()
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
        //Không có plan
        if (stepRecords.isEmpty()) {
//            throw new AppException(ErrorCode.STEP_PLAN_NOT_FOUND);
            return false;
        }
        // có mà chưa nhập
        if (stepRecords.get().getPlannedStepPerDay() <= 0) {
//            throw new AppException(ErrorCode.STEP_DAY_DATA_EMPTY);
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkPlanExist(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        Date weekStartNow = formatDate.parse(weekStart);
        List<StepRecord> stepRecordsList = stepRecordRepository.findByAppUserId(appUser.get().getId());
        Optional<StepRecord> stepRecords = stepRecordsList.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartNow);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        //Không có plan
        if (stepRecords.isEmpty()) {
            return false;
        }
        if (stepRecords.get().getPlannedStepPerDay() <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public void updateStepRecordNEW(StepRecordUpdateContinuousDTO stepRecordDTO) throws ParseException {
        Optional<AppUser> appUser = appUserRepository.findByIdActivated(stepRecordDTO.getUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        String dateStr = formatDate.format(stepRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<StepRecord> stepPlanExist = stepRecordRepository.findByAppUserId(appUser.get().getId());
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
            StepRecord stepRecordNew = StepRecord.builder()
                    .appUserId(appUser.get())
                    .date(stepRecordDTO.getDate())
                    .actualValue(stepRecordDTO.getActualValue())
                    .plannedStepPerDay(0)
                    .weekStart(getFirstDayOfWeek(stepRecordDTO.getDate()))
                    .build();
            stepRecordRepository.save(stepRecordNew);
            System.out.println("Create new record for user " + appUser.get().getId() + " at " + stepRecordDTO.getDate() + "with value " + stepRecordDTO.getActualValue());
        } else {
            StepRecord stepRecordNew = getStepRecordById(stepRecord.get().getId());
            stepRecordNew.setActualValue(stepRecordNew.getActualValue() + stepRecordDTO.getActualValue());
            stepRecordRepository.save(stepRecordNew);
            System.out.println("Update new record for user " + appUser.get().getId() + " at " + stepRecordDTO.getDate() + "with value " + stepRecordDTO.getActualValue());
        }
    }

    @Override
    public CurrentStepRecordResponseDTO getCurrentRecord() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Integer currentValue = 0;
        Integer planedValue = 0;

        Date today = DateUtils.getToday(formatDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(today);
        Date date = sdf.parse(formattedDate);
        Optional<StepRecord> stepRecord = stepRecordRepository.findByAppUserIdAndDate(appUser.get().getId(), date);
        if (!stepRecord.isEmpty()) {
            // convert to int
            currentValue = Math.round(stepRecord.get().getActualValue());
            planedValue = stepRecord.get().getPlannedStepPerDay();
        }
        log.info("Get current record for user " + appUser.get().getId() + " at " + date);
        CurrentStepRecordResponseDTO currentStepRecordResponseDTO = CurrentStepRecordResponseDTO.builder()
                .currentValue(currentValue)
                .planedValue(planedValue)
                .build();
        return currentStepRecordResponseDTO;
    }


    public Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Set the first day of the week to Monday
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Get the current day of the week
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Calculate the difference to Monday
        int diff = Calendar.MONDAY - dayOfWeek;
        // If the current day is Sunday, adjust the difference
        if (dayOfWeek == Calendar.SUNDAY) {
            diff = -6;
        }
        // Add the difference to the calendar date
        calendar.add(Calendar.DAY_OF_MONTH, diff);
        return calendar.getTime();
    }


}