package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.BloodPressureRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.BloodPressureRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.BloodPressureRecordService;

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

    @Override
    public BloodPressureRecord createBloodPressureRecord(BloodPressureRecordDTO bloodPressureRecordDTO) throws ParseException {
        BloodPressureRecord bloodPressureRecord = BloodPressureRecord.builder()
                .diastole(bloodPressureRecordDTO.getDiastole())
                .systole(bloodPressureRecordDTO.getSystole())
                .weekStart(bloodPressureRecordDTO.getWeekStart())
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

    @Override
    public BloodPressureRecord updateBloodPressureRecord(Integer id, BloodPressureRecordDTO bloodPressureRecordDTO) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(id);
        bloodPressureRecord.setDiastole(bloodPressureRecordDTO.getDiastole());
        bloodPressureRecord.setSystole(bloodPressureRecordDTO.getSystole());
        bloodPressureRecord.setWeekStart(bloodPressureRecordDTO.getWeekStart());
        bloodPressureRecord.setDate(bloodPressureRecordDTO.getDate());
        return bloodPressureRecordRepository.save(bloodPressureRecord);
    }

    @Override
    public BloodPressureRecord deleteBloodPressureRecord(Integer id) {
        BloodPressureRecord bloodPressureRecord = getBloodPressureRecordById(id);
        bloodPressureRecordRepository.deleteById(id);
        return bloodPressureRecord;
    }

    @Override
    public BloodPressureResponseChartDTO getDataChart() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);

        int count = 5;
        BloodPressureResponseChartDTO bloodPressureResponseChartDTO = new BloodPressureResponseChartDTO();
        List<BloodPressureResponse> bloodPressureResponseList = new ArrayList<>();
        List<BloodPressureRecord> bloodPressureRecordListExits = bloodPressureRecordRepository.findAllByUserId(appUser.get().getId());

        //Sắp xếp giảm dần theo date
        bloodPressureRecordListExits.sort(new Comparator<BloodPressureRecord>() {
            @Override
            public int compare(BloodPressureRecord recordDateSmaller, BloodPressureRecord recordDateBigger) {
                return recordDateBigger.getDate().compareTo(recordDateSmaller.getDate());
            }
        });

        Optional<BloodPressureRecord> bloodPressureRecordByDate = bloodPressureRecordListExits.stream()
                .filter(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        String sortedDateStr = formatDate.format(date);
                        Date parsedSortedDate = formatDate.parse(sortedDateStr);
                        return recordDate.equals(parsedSortedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst();
        bloodPressureResponseChartDTO.setSystoleToday(
                bloodPressureRecordByDate.map(BloodPressureRecord::getSystole).orElse((float) 0)
        );
        bloodPressureResponseChartDTO.setDiastoleToday(
                bloodPressureRecordByDate.map(BloodPressureRecord::getDiastole).orElse((float) 0)
        );

            for (BloodPressureRecord bloodPressureRecord : bloodPressureRecordListExits) {
                String smallerDateStr= formatDate.format(bloodPressureRecord.getDate());
                Date smallerDate = formatDate.parse(smallerDateStr);
                BloodPressureResponse bloodPressureResponse = new BloodPressureResponse();
                if(smallerDate.before(date) || smallerDate.equals(date)){
                    bloodPressureResponse.setDate(bloodPressureRecord.getDate());
                    bloodPressureResponse.setSystole(bloodPressureRecord.getSystole());
                    bloodPressureResponse.setDiastole(bloodPressureRecord.getDiastole());
                    bloodPressureResponseList.add(bloodPressureResponse);
                    count--;
                }
                today = calculateDate(today,1);
                if(count < 1){
                    break;
                }
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