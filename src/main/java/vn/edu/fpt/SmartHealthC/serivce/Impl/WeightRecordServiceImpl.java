package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.StepRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.WeightRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.WeightRecordService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeightRecordServiceImpl implements WeightRecordService {

    @Autowired
    private WeightRecordRepository weightRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SimpleDateFormat formatDate;
    @Autowired
    private StepRecordRepository stepRecordRepository;

    @Transactional
    @Override
    public WeightRecord createWeightRecord(WeightRecordDTO weightRecordDTO) throws ParseException {
        WeightRecord weightRecord =  WeightRecord.builder()
                .weekStart(weightRecordDTO.getWeekStart())
                .weight(weightRecordDTO.getWeight())
                .date(weightRecordDTO.getDate()).build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        String dateStr= formatDate.format(weightRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<WeightRecord> weightRecordListExits = weightRecordRepository.findAppUser(appUser.get().getId());
        boolean dateExists = weightRecordListExits.stream()
                .anyMatch(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                });
        if (dateExists) {
            throw new AppException(ErrorCode.WEIGHT_RECORD_DAY_EXIST);
        }
        weightRecord.setAppUserId(appUser.get());
        return  weightRecordRepository.save(weightRecord);
    }

    @Override
    public WeightRecord getWeightRecordById(Integer id) {
        Optional<WeightRecord> weightRecord  = weightRecordRepository.findById(id);
        if(weightRecord.isEmpty()){
            throw new AppException(ErrorCode.WEIGHT_RECORD_NOT_FOUND);
        }
        return weightRecord.get();
    }

    @Override
    public List<WeightRecord> getAllWeightRecords() {
        return weightRecordRepository.findAll();
    }

    @Override
    public List<WeightResponseDTO> getWeightRecordList(Integer userId) {
        List<Date> weightRecordWeek = weightRecordRepository.findDistinctWeek(userId);
        List<WeightResponseDTO> responseDTOList = new ArrayList<>();
        for (Date week : weightRecordWeek) {
            WeightResponseDTO weightResponseDTO = WeightResponseDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(weightResponseDTO);
        }

        for (WeightResponseDTO record : responseDTOList) {
            List<WeightRecord> weightRecordByWeek = weightRecordRepository.findByWeekStart(record.getWeekStart(), userId);
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            Float weight = 0f;
            int count = 0;
            for (WeightRecord weightRecord : weightRecordByWeek) {
                RecordPerDay recordPerDay = RecordPerDay.builder()
                        .date(weightRecord.getDate())
                        .weight(weightRecord.getWeight()+"kg")
                        .build();
                recordPerDayList.add(recordPerDay);
                //sort by  Date date;
                recordPerDayList.sort(Comparator.comparing(RecordPerDay::getDate));
                if (weightRecord.getWeight() != null) {
                    weight +=weightRecord.getWeight();
                    count++;
                }
            }
            if (count != 0) {
                weight = weight / count;
                weight = (float) (Math.round(weight * 100) / 100);
            }
            record.setAvgValue(weight + "kg");
            record.setRecordPerDayList(recordPerDayList);
        }
        return responseDTOList;
    }

    @Override
    public WeightResponseChartDTO getDataChart() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);


        List<WeightRecord> weightRecordList = weightRecordRepository.findAppUser(appUser.get().getId());
        //Sắp xếp giảm dần theo date
        weightRecordList.sort(new Comparator<WeightRecord>() {
            @Override
            public int compare(WeightRecord recordDateSmaller, WeightRecord recordDateBigger) {
                return recordDateBigger.getDate().compareTo(recordDateSmaller.getDate());
            }
        });
        int count = 5;
        double sumValue = 0;
        WeightResponseChartDTO weightResponseChartDTO = new WeightResponseChartDTO();
        List<WeightResponse> weightResponseList = new ArrayList<>();
        for (WeightRecord weightRecord : weightRecordList) {
            String smallerDateStr= formatDate.format(weightRecord.getDate());
            Date smallerDate = formatDate.parse(smallerDateStr);

            if(smallerDate.before(date)){
                WeightResponse weightResponse = new WeightResponse().builder()
                        .date(weightRecord.getDate()).value(weightRecord.getWeight()).build();
                count--;
                sumValue+=weightRecord.getWeight();
                weightResponseList.add(weightResponse);
            }
            if(smallerDate.equals(date)){
                WeightResponse weightResponse = new WeightResponse().builder()
                        .date(weightRecord.getDate()).value(weightRecord.getWeight()).build();
                count--;
                sumValue+=weightRecord.getWeight();
                weightResponseChartDTO.setValueToday(weightRecord.getWeight());
                weightResponseList.add(weightResponse);
            }
            today = calculateDate(today,1);
            if(count < 1){
                break;
            }
        }
        //sắp xếp tăng dần theo date
        weightResponseList.sort(new Comparator<WeightResponse>() {
            @Override
            public int compare(WeightResponse recordDateSmaller, WeightResponse recordDateBigger) {
                return recordDateSmaller.getDate().compareTo(recordDateBigger.getDate());
            }
        });
        weightResponseChartDTO.setWeightResponseList(weightResponseList);
        weightResponseChartDTO.setAvgValue((int) (sumValue/weightResponseChartDTO.getWeightResponseList().stream().count()));
        return  weightResponseChartDTO;
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
        List<WeightRecord> weightRecordList = weightRecordRepository.findAppUser(appUser.get().getId());
        Optional<WeightRecord> weightRecord = weightRecordList.stream()
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
        //Hôm nay chưa nhập liệu
        if (weightRecord.isEmpty()) {
//            throw new AppException(ErrorCode.WEIGHT_DATA_DAY_EMPTY);
        return false;
        }
        return true;
    }

    public Date calculateDate(Date sourceDate , int minus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, -minus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }

    @Transactional
    @Override
    public void updateWeightRecord(Integer id, WeightRecordDTO weightRecordDTO) {

        WeightRecord weightRecord = getWeightRecordById(id);
        weightRecord.setWeight(weightRecordDTO.getWeight());
        weightRecordRepository.save(weightRecord);
    }

    @Override
    public WeightRecord deleteWeightRecord(Integer id) {
        WeightRecord weightRecord = getWeightRecordById(id);
        weightRecordRepository.deleteById(id);
        return weightRecord;
    }


}