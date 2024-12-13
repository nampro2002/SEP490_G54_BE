package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.BloodPressureRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BloodPressureRecordServiceImpl implements BloodPressureRecordService {

    @Autowired
    private BloodPressureRecordRepository bloodPressureRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SimpleDateFormat formatDate;
    @Transactional
    @Override
    public BloodPressureRecord createBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO) throws ParseException {
        BloodPressureRecord bloodPressureRecord = BloodPressureRecord.builder()
                .diastole(bloodPressureRecordDTO.getDiastole())
                .systole(bloodPressureRecordDTO.getSystole())
                .weekStart(DateUtils.normalizeDate(formatDate,formatDate.format(bloodPressureRecordDTO.getWeekStart())))
                .date(bloodPressureRecordDTO.getDate()).build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        String dateStr= formatDate.format(bloodPressureRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        List<BloodPressureRecord> bloodPressureRecordListExits = bloodPressureRecordRepository.findAllByUserId(appUser.get().getId());
        boolean dateExists = bloodPressureRecordListExits.stream()
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
            throw new AppException(ErrorCode.BLOOD_PRESSURE_DAY_EXIST);
        }

        bloodPressureRecord.setAppUserId(appUser.get());
        return bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public BloodPressureRecord getBloodPressureRecordById(Integer id) {
        Optional<BloodPressureRecord> bloodPressureRecord = bloodPressureRecordRepository.findById(id);
        if (bloodPressureRecord.isEmpty()) {
            throw new AppException(ErrorCode.BLOOD_PRESSURE_NOT_FOUND);
        }
        return bloodPressureRecord.get();
    }

    @Override
    public List<BloodPressureRecord> getAllBloodPressureRecords() {
        return bloodPressureRecordRepository.findAll();
    }

    @Override
    public List<BloodPressureResponseDTO> getListBloodPressureRecordsByUser(Integer userId) {
        List<Date> bloodPressureWeekList = bloodPressureRecordRepository.findDistinctWeek(userId);
        List<BloodPressureResponseDTO> responseDTOList = new ArrayList<>();
        for (Date week : bloodPressureWeekList) {
            BloodPressureResponseDTO bloodPressureResponseDTO = BloodPressureResponseDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(bloodPressureResponseDTO);
        }

        for (BloodPressureResponseDTO record : responseDTOList) {
            List<BloodPressureRecord> bloodPressureRecords = bloodPressureRecordRepository.findByWeekStart(record.getWeekStart(), userId);
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            Float systole = 0f;
            Float diastole = 0f;
            int countSystole = 0;
            int countDiastole = 0;
            for (BloodPressureRecord bloodPressureRecord : bloodPressureRecords) {
                RecordPerDay recordPerDay = RecordPerDay.builder()
                        .date(bloodPressureRecord.getDate())
                        .systole(bloodPressureRecord.getSystole()+ "mmHG")
                        .diastole(bloodPressureRecord.getDiastole()+ "mmHG")
                        .build();
                recordPerDayList.add(recordPerDay);
                //sort by  Date date;
                recordPerDayList.sort(Comparator.comparing(RecordPerDay::getDate));
                if (bloodPressureRecord.getSystole() != null) {
                    systole += bloodPressureRecord.getSystole();
                    countSystole++;
                }
                if (bloodPressureRecord.getDiastole() != null) {
                    diastole += bloodPressureRecord.getDiastole();
                    countDiastole++;
                }
            }
            if (countSystole != 0) {
                systole = systole / countSystole;
                systole = (float) (Math.round(systole * 100) / 100);
            }
            if (countDiastole != 0) {
                diastole = diastole / countDiastole;
                diastole = (float) (Math.round(diastole * 100) / 100);
            }
            record.setAvgValue(systole + "mmHG" + " / " + diastole + "mmHG");
            record.setRecordPerDayList(recordPerDayList);
        }
        return responseDTOList;
    }
    @Transactional
    @Override
    public void updateBloodPressureRecord(Integer id, BloodPressureRecordDTO bloodPressureRecordDTO) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(id);
        bloodPressureRecord.setDiastole(bloodPressureRecordDTO.getDiastole());
        bloodPressureRecord.setSystole(bloodPressureRecordDTO.getSystole());
        bloodPressureRecord.setWeekStart(bloodPressureRecordDTO.getWeekStart());
        bloodPressureRecord.setDate(bloodPressureRecordDTO.getDate());
        bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public BloodPressureRecord deleteBloodPressureRecord(Integer id) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(id);
        bloodPressureRecordRepository.deleteById(id);
        return bloodPressureRecord;
    }

    @Override
    public BloodPressureResponseChartDTO getDataChart() throws ParseException {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Date dateToday = DateUtils.getToday(formatDate);

        BloodPressureResponseChartDTO bloodPressureResponseChartDTO = new BloodPressureResponseChartDTO();
        List<BloodPressureResponse> bloodPressureResponseList = new ArrayList<>();
        List<BloodPressureRecord> bloodPressureRecordListExits = bloodPressureRecordRepository.find5RecordByIdUser(appUser.getId());
            for (BloodPressureRecord bloodPressureRecord : bloodPressureRecordListExits) {
                BloodPressureResponse bloodPressureResponse = new BloodPressureResponse();
                    bloodPressureResponse.setDate(bloodPressureRecord.getDate());
                    bloodPressureResponse.setSystole(bloodPressureRecord.getSystole());
                    bloodPressureResponse.setDiastole(bloodPressureRecord.getDiastole());
                    bloodPressureResponseList.add(bloodPressureResponse);

            }
            if(!bloodPressureResponseList.isEmpty()){
                String todayStr = formatDate.format(bloodPressureRecordListExits.get(0).getDate());
                Date todayDate = formatDate.parse(todayStr);
                if(dateToday.equals(todayDate)){
                    bloodPressureResponseChartDTO.setSystoleToday(bloodPressureRecordListExits.get(0).getSystole());
                    bloodPressureResponseChartDTO.setDiastoleToday(bloodPressureRecordListExits.get(0).getDiastole());
                }else{
                    bloodPressureResponseChartDTO.setSystoleToday(0.f);
                    bloodPressureResponseChartDTO.setDiastoleToday(0.f);
                }
            }else{
                bloodPressureResponseChartDTO.setSystoleToday(0.f);
                bloodPressureResponseChartDTO.setDiastoleToday(0.f);
            }
            //sắp xếp tăng dần theo date
        bloodPressureResponseList.sort(new Comparator<BloodPressureResponse>() {
            @Override
            public int compare(BloodPressureResponse recordDateSmaller, BloodPressureResponse recordDateBigger) {
                return recordDateSmaller.getDate().compareTo(recordDateBigger.getDate());
            }
        });
        bloodPressureResponseChartDTO.setBloodPressureResponseList(bloodPressureResponseList);
        return bloodPressureResponseChartDTO;
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
        List<BloodPressureRecord> bloodPressureRecordList = bloodPressureRecordRepository.findAllByUserId(appUser.get().getId());
        Optional<BloodPressureRecord> bloodPressureRecord = bloodPressureRecordList.stream()
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
        if (bloodPressureRecord.isEmpty()) {
//            throw new AppException(ErrorCode.BLOOD_PRESSURE_DATA_DAY_EMPTY);
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


}