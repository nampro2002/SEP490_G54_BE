package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicinePLanResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicinePlanPerDayResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicineResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicineResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicineTypeRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicineRecordServiceImpl implements MedicineRecordService {
    @Autowired
    private MedicineRecordRepository medicineRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MedicineTypeRepository medicineTypeRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SimpleDateFormat formatDate;

    @Transactional
    @Override
    public void createMedicineRecord(List<MedicineRecordCreateDTO> medicineRecordDTOList) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

//        // Kiểm tra trùng lặp MedicineTypeId
//        HashSet<Integer> checkDuplicate = new HashSet<>();
//        for (MedicineRecordCreateDTO medicineRecordCreateDTO : medicineRecordDTOList) {
//            int uniqueIdentifier = medicineRecordCreateDTO.getMedicineTypeId();
//                if (checkDuplicate.contains(uniqueIdentifier)) {
//                    throw new AppException(ErrorCode.MEDICINE_TYPE_LIST_DUPLICATE);
//                }else{
//                    checkDuplicate.add(uniqueIdentifier);
//                }
//        }

        for (MedicineRecordCreateDTO medicineRecordCreateDTO : medicineRecordDTOList) {
            //Check plan với week start và type medicine có trong tuần chưa
            String weekStartStr = formatDate.format(medicineRecordCreateDTO.getWeekStart());
            Date weekStart = formatDate.parse(weekStartStr);
            List<MedicineRecord> medicinePlanExist = medicineRecordRepository.findByAppUser(appUser.get().getId());
            boolean dateExists = medicinePlanExist.stream()
                    .anyMatch(record -> {
                        String recordDateStr = formatDate.format(record.getWeekStart());
                        try {
                            Date recordDate = formatDate.parse(recordDateStr);
                            return recordDate.equals(weekStart)
                                    && record.getMedicineType().getId().equals(medicineRecordCreateDTO.getMedicineTypeId());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
            if (dateExists) {
                throw new AppException(ErrorCode.MEDICINE_PLAN_EXIST);
            }
        }
        for (MedicineRecordCreateDTO medicineRecordCreateDTO : medicineRecordDTOList) {
            //Check type medicine có tồn tại chưa
            Optional<MedicineType> medicineType = medicineTypeRepository.findById(medicineRecordCreateDTO.getMedicineTypeId());
            if (medicineType.isEmpty()) {
                throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
            }
            //add by schedule
            for (Date date : medicineRecordCreateDTO.getSchedule()) {

                MedicineRecord medicineRecord = MedicineRecord.builder()
                        .weekStart(DateUtils.normalizeDate(formatDate, formatDate.format(medicineRecordCreateDTO.getWeekStart())))
                        .build();
                medicineRecord.setAppUserId(appUser.get());
                medicineRecord.setMedicineType(medicineType.get());
                medicineRecord.setDate(date);
                medicineRecordRepository.save(medicineRecord);
            }
        }


    }

    public Date calculateDate(Date date, int plus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, plus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }

    @Override
    public MedicineRecordResponseDTO getMedicineRecordById(Integer id) {
        Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findById(id);
        if (medicineRecord.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }

        return MedicineRecordResponseDTO.builder()
                .id(medicineRecord.get().getId())
                .appUserName(medicineRecord.get().getAppUserId().getName())
                .medicineType(medicineRecord.get().getMedicineType().getTitle())
                .weekStart(medicineRecord.get().getWeekStart())
                .date(medicineRecord.get().getDate())
                .status(medicineRecord.get().getStatus())
                .build();
    }

    @Override
    public MedicineRecord getMedicineRecordEntityById(Integer id) {
        Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findById(id);
        if (medicineRecord.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }

        return medicineRecord.get();
    }

    @Override
    public List<MedicineRecordListResDTO> getAllMedicineRecords(Integer userId) {
        List<Date> medicineDate = medicineRecordRepository.findDistinctDate(userId);
        List<MedicineRecordListResDTO> responseDTOList = new ArrayList<>();
        for (Date week : medicineDate) {
            MedicineRecordListResDTO medicineRecordListResDTO = MedicineRecordListResDTO.builder()
                    .date(week)
                    .build();
            responseDTOList.add(medicineRecordListResDTO);
        }
        for (MedicineRecordListResDTO medicineRecord : responseDTOList) {
            List<MedicineRecord> medicineRecords = medicineRecordRepository.findByDate(medicineRecord.getDate(), userId);
            int count = 0;
            for (MedicineRecord record : medicineRecords) {
                if(record.getStatus()==null){
                    continue;
                }
                if (record.getStatus()) {
                    count++;
                }
            }
            medicineRecord.setMedicineStatus(count + "/" + medicineRecords.size());
        }
        return responseDTOList;
    }

    @Transactional
    @Override
    public void updateMedicineRecord(MedicineRecordUpdateDTO medicineRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        String dateStr = formatDate.format(medicineRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);

        List<MedicineRecord> planExist = medicineRecordRepository.findByAppUser(appUser.get().getId());
        List<MedicineRecord> planDateExist = planExist.stream()
                .filter(record -> {
                    try {
                        String recordDateStr = formatDate.format(record.getDate());
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).toList();
        if (planDateExist.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_DAY_NOT_FOUND);
        }

        //Lấy ra toàn bộ thuốc của ngày hôm đó
        Set<Integer> medicineTypeExist = new HashSet<>();
        for (MedicineRecord medicineRecord : planDateExist) {
            String recordDateStr = formatDate.format(medicineRecord.getDate());
            Date recordDate = formatDate.parse(recordDateStr);
            if (recordDate.equals(date)) {
                if (!medicineTypeExist.contains(medicineRecord.getMedicineType().getId())) {
                    medicineTypeExist.add(medicineRecord.getMedicineType().getId());
                }
            }
        }
        //Check xem medicineType truyền về có tồn tại ko
        for (Integer rule : medicineRecordDTO.getMedicineTypeId()) {
            boolean ruleExists = planDateExist.stream()
                    .anyMatch(record -> {
                        return record.getMedicineType().getId().equals(rule);
                    });
            if (ruleExists == false) {
                throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
            }
        }
        //Check xem medicine exist với medicine truyền
        //nếu medicine exist contain thì true
        // ko contain thì false
        for (Integer type : medicineTypeExist) {
            Optional<MedicineRecord> medicineRecord = planDateExist.stream()
                    .filter(record -> {
                        String recordDateStr = formatDate.format(record.getDate());
                        try {
                            Date recordDate = formatDate.parse(recordDateStr);
                            return recordDate.equals(date)
                                    && record.getMedicineType().getId().equals(type);
                        } catch (ParseException e) {
                            return false;
                        }
                    })
                    .findFirst();
//            if (medicineRecord.isEmpty()) {
//                throw new AppException(ErrorCode.MEDICINE_DAY_NOT_FOUND);
//            }
            MedicineRecord medicineRecordUpdate = getMedicineRecordEntityById(medicineRecord.get().getId());
            medicineRecordUpdate.setDate(medicineRecordDTO.getDate());
            if (medicineRecordDTO.getMedicineTypeId().contains(type)) {
                medicineRecordUpdate.setStatus(medicineRecordDTO.getStatus());
            } else {
                medicineRecordUpdate.setStatus(false);
            }

            medicineRecordRepository.save(medicineRecordUpdate);
        }
    }

    @Override
    public MedicineRecordResponseDTO deleteMedicineRecord(Integer id) {
        MedicineRecord medicineRecord = getMedicineRecordEntityById(id);
        medicineRecordRepository.deleteById(id);
        return MedicineRecordResponseDTO.builder()
                .id(medicineRecord.getId())
                .weekStart(medicineRecord.getWeekStart())
                .date(medicineRecord.getDate())
                .status(medicineRecord.getStatus())
                .build();
    }

    public static int getDayOfWeekNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Chuyển đổi ngày trong tuần thành số theo quy ước
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return 2;
            case Calendar.TUESDAY:
                return 3;
            case Calendar.WEDNESDAY:
                return 4;
            case Calendar.THURSDAY:
                return 5;
            case Calendar.FRIDAY:
                return 6;
            case Calendar.SATURDAY:
                return 7;
            case Calendar.SUNDAY:
                return 0;
            default:
                throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
        }
    }

    private static Date truncateTime(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(date));
    }

    @Override
    public List<MedicinePLanResponseDTO> getAllMedicinePlans(String weekStart) throws ParseException {

        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);

        Date weekStartPlan = formatDate.parse(weekStart);
        List<MedicineRecord> weekPlan = medicineRecordRepository.findByAppUserAndWeekStart(appUser.getId(), weekStartPlan);
        Map<String, List<MedicineRecord>> filteredSchedules = new HashMap<>();

        for (MedicineRecord medicineRecord : weekPlan) {
            String key = medicineRecord.getMedicineType().getId() + ";" + getTime(medicineRecord.getDate());
            if (!filteredSchedules.containsKey(key)) {
                filteredSchedules.put(key, new ArrayList<>());
            }
            filteredSchedules.get(key).add(medicineRecord);
        }

        List<MedicinePLanResponseDTO> medicinePlanResponseDTOList = new ArrayList<>();
        for (Map.Entry<String, List<MedicineRecord>> entry : filteredSchedules.entrySet()) {
            String[] keyParts = entry.getKey().split(";");
            String type = keyParts[0];
            String time = keyParts[1];
            List<MedicineRecord> medicineRecordsByTimeAndType = entry.getValue();
            MedicinePLanResponseDTO medicinePLanResponseDTO2 = new MedicinePLanResponseDTO();
            medicinePLanResponseDTO2.setMedicineTypeId(Integer.parseInt(type));
            Optional<MedicineType> medicineType = medicineTypeRepository.findById(Integer.parseInt(type));
            medicinePLanResponseDTO2.setMedicineTitle(medicineType.get().getTitle());
            medicinePLanResponseDTO2.setTime(time);
            for (MedicineRecord medicineRecord : medicineRecordsByTimeAndType) {
                medicinePLanResponseDTO2.getWeekday().add(getDayOfWeek(medicineRecord.getDate()));
                medicinePLanResponseDTO2.getWeekTime().add(medicineRecord.getDate());
                medicinePLanResponseDTO2.getIndexDay().add(getDayOfWeekNumber(medicineRecord.getDate()));
            }
            medicinePlanResponseDTOList.add(medicinePLanResponseDTO2);
        }
        return medicinePlanResponseDTOList;

    }

    private static boolean checkTime(Date date, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date).equals(time);
    }

    @Override
    public MedicineResponseChartDTO getDataChart() throws ParseException {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Date date = DateUtils.getToday(formatDate);


        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(appUser.getId());
        //Sắp xếp giảm dần theo date
        medicineRecordList.sort(new Comparator<MedicineRecord>() {
            @Override
            public int compare(MedicineRecord recordDateSmaller, MedicineRecord recordDateBigger) {
                return recordDateBigger.getDate().compareTo(recordDateSmaller.getDate());
            }
        });
        MedicineResponseChartDTO medicineResponseChartDTO = new MedicineResponseChartDTO();
        List<MedicineResponse> medicineResponseList = new ArrayList<>();
        //Lấy ra 5 date gần nhất và có value không bị null (1 thằng bị null trong ngày đó cx ko lấy)
        Set<Date> uniqueDates = new HashSet<>();
        for (MedicineRecord record : medicineRecordList) {
            String recordDateStr = formatDate.format(record.getDate());
            Date recordDate = formatDate.parse(recordDateStr);
            if (recordDate.before(date) || recordDate.equals(date)) {
                boolean dataNullExists = medicineRecordList.stream()
                        .anyMatch(item -> {
                            String itemDateStr = formatDate.format(item.getDate());
                            try {
                                Date itemDate = formatDate.parse(itemDateStr);
                                String sortedDateStr = formatDate.format(record.getDate());
                                Date parsedSortedDate = formatDate.parse(sortedDateStr);
                                return itemDate.equals(parsedSortedDate)
                                        && item.getStatus() == null;
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        });
                // ko có value nào bị null
                if (dataNullExists == false) {
                    if (!uniqueDates.contains(recordDate)) {
                        uniqueDates.add(recordDate);
                    }
                }
                if (uniqueDates.size() == 5) {
                    break;
                }
            }
        }
        //Sắp xếp date tăng dần
        Set<Date> sortedDates = new TreeSet<>(uniqueDates);
        //Tìm danh sách theo date
        for (Date sortedDate : sortedDates) {
            if (sortedDate.equals(date)) {
                List<MedicineRecord> listByDate = medicineRecordList.stream()
                        .filter(record -> {
                            String recordDateStr = formatDate.format(record.getDate());
                            try {
                                Date recordDate = formatDate.parse(recordDateStr);
                                String sortedDateStr = formatDate.format(sortedDate);
                                Date parsedSortedDate = formatDate.parse(sortedDateStr);
                                return recordDate.equals(parsedSortedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .toList();
                int total = listByDate.size();
                int done = Math.toIntExact(listByDate.stream()
                        .filter(MedicineRecord::getStatus)
                        .count());
                double valuePercent = ((double) done / total) * 100;
                medicineResponseChartDTO.setDoneToday(done);
                medicineResponseChartDTO.setTotalToday(total);

                MedicineResponse medicineResponse = new MedicineResponse();
                medicineResponse.setDate(sortedDate);
                Integer value = (int) Math.round(valuePercent);
                medicineResponse.setValuePercent(value);
                medicineResponseList.add(medicineResponse);
            } else {
                List<MedicineRecord> listByDate = medicineRecordList.stream()
                        .filter(record -> {
                            String recordDateStr = formatDate.format(record.getDate());
                            try {
                                Date recordDate = formatDate.parse(recordDateStr);
                                String sortedDateStr = formatDate.format(sortedDate);
                                Date parsedSortedDate = formatDate.parse(sortedDateStr);
                                return recordDate.equals(parsedSortedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .toList();
                int total = listByDate.size();
                int done = Math.toIntExact(listByDate.stream()
                        .filter(MedicineRecord::getStatus)
                        .count());
                double valuePercent = ((double) done / total) * 100;
                MedicineResponse medicineResponse = new MedicineResponse();
                medicineResponse.setDate(sortedDate);
                Integer value = (int) Math.round(valuePercent);
                medicineResponse.setValuePercent(value);
                medicineResponseList.add(medicineResponse);
            }
        }
        medicineResponseChartDTO.setMedicineResponseList(medicineResponseList);
        return medicineResponseChartDTO;
    }

    @Override
    public List<MedicinePlanPerDayResponse> getMedicinePerDay(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        Date today = new Date();
        String dateStr = formatDate.format(today);
        Date date = formatDate.parse(dateStr);
        List<MedicinePlanPerDayResponse> medicinePlanPerDayResponseList = new ArrayList<>();
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(appUser.get().getId());
        List<MedicineRecord> medicineRecordDayList = medicineRecordList.stream()
                .filter(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(date);
                    } catch (ParseException e) {
                        return false;
                    }
                }).toList();
        for (MedicineRecord record : medicineRecordDayList) {
            medicinePlanPerDayResponseList.add(new MedicinePlanPerDayResponse().builder()
                    .id(record.getId())
                    .medicineName(record.getMedicineType().getTitle())
                    .medicineId(record.getMedicineType().getId())
                    .date(record.getDate()).build());
        }
        return medicinePlanPerDayResponseList;
    }

    public Date calculateDateMinus(Date sourceDate, int minus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, -minus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }

    // Hàm xác định thứ trong tuần từ Date
    public static String getDayOfWeek(Date date) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        return dayFormat.format(date);
    }

    // Hàm xác định thời gian (giờ, phút, giây) từ Date
    public static String getTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(date);
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
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(appUser.get().getId());
        List<MedicineRecord> medicineRecord = medicineRecordList.stream()
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
                .toList();
        //Không có plane
        if (medicineRecord.isEmpty()) {
//            throw new AppException(ErrorCode.MEDICINE_PLAN_NOT_FOUND);
            return false;
        }
        //Có mà chưa nhập
        for (MedicineRecord record : medicineRecord) {
            if (record.getStatus() == null) {
//                throw new AppException(ErrorCode.MEDICINE_DAY_DATA_EMPTY);
                return false;
            }
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
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(appUser.get().getId());
        List<MedicineRecord> medicineRecord = medicineRecordList.stream()
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
        //Không có plan
        if (medicineRecord.isEmpty()) {
//            throw new Appxception(ErrorCode.MEDICINE_PLAN_NOT_FOUND);
            return false;
        }
        return true;
    }
//    public List<MedicineRecord> getRecordByDateTime() throws ParseException {
//        Date date = DateUtils.getToday(formatDate);
//        return medicineRecordRepository.findByDate(date);
//    }
}
