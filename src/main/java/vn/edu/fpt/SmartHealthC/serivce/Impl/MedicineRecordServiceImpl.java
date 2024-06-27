package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO.DietResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO.MedicinePLanResponseDTO;
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
    @Override
    public void createMedicineRecord(List<MedicineRecordCreateDTO> medicineRecordDTOList) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);

        //Check plan
        for (MedicineRecordCreateDTO medicineRecordCreateDTO : medicineRecordDTOList){
        String weekStartStr= formatDate.format(medicineRecordCreateDTO.getWeekStart());
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

        for (MedicineRecordCreateDTO medicineRecordCreateDTO : medicineRecordDTOList){
            Optional<MedicineType> medicineType = medicineTypeRepository.findById(medicineRecordCreateDTO.getMedicineTypeId());
            if (medicineType.isEmpty()) {
                throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
            }
            //Check type medicine
            List<MedicineRecord> medicinePlanExist = medicineRecordRepository.findByAppUser(appUser.get().getId());
            boolean dateExists = medicinePlanExist.stream()
                    .anyMatch(record -> {
                        return record.getMedicineType().getId().equals(medicineRecordCreateDTO.getMedicineTypeId());
                    });
            if (dateExists) {
                throw new AppException(ErrorCode.MEDICINE_TYPE_EXIST);
            }

            //Check schedule
            for(Date date : medicineRecordCreateDTO.getSchedule()){

                MedicineRecord medicineRecord = MedicineRecord.builder()
                        .weekStart(medicineRecordCreateDTO.getWeekStart())
                        .status(false)
                        .build();
                medicineRecord.setAppUserId(appUser.get());
                medicineRecord.setMedicineType(medicineType.get());
                medicineRecord.setDate(date);
                medicineRecordRepository.save(medicineRecord);
            }
        }

    }
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
                if (record.getStatus()) {
                    count++;
                }
            }
            medicineRecord.setMedicineStatus(count+"/"+medicineRecords.size());
        }
        return responseDTOList;
    }

    @Override
    public MedicineRecordResponseDTO updateMedicineRecord(MedicineRecordUpdateDTO medicineRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        String dateStr= formatDate.format(medicineRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        boolean ruleExists = false;
        List<MedicineRecord> planExist = medicineRecordRepository.findByAppUser(appUser.get().getId());
        for (Integer rule : medicineRecordDTO.getMedicineTypeId()){
            ruleExists = planExist.stream()
                    .anyMatch(record -> {
                        try {
                            String recordDateStr = formatDate.format(record.getDate());
                            Date recordDate = formatDate.parse(recordDateStr);
                            return record.getMedicineType().getId().equals(rule) && recordDate.equals(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
        }
        if (!ruleExists) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }

        for (Integer type : medicineRecordDTO.getMedicineTypeId()){
            Optional<MedicineRecord> medicineRecord = planExist.stream()
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
            if (medicineRecord.isEmpty()) {
                throw new AppException(ErrorCode.MEDICINE_DAY_NOT_FOUND);
            }
           MedicineRecord medicineRecordUpdate =getMedicineRecordEntityById(medicineRecord.get().getId());
            medicineRecordUpdate.setDate(medicineRecordDTO.getDate());
            medicineRecordUpdate.setStatus(medicineRecordDTO.getStatus());
            medicineRecordRepository.save(medicineRecordUpdate);
        }

        return null;
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

    @Override
    public List<MedicinePLanResponseDTO> getAllMedicinePlans(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);

        //Ngày hôm nay
        Date today = new Date();
        String dateTodayStr= formatDate.format(today);
        Date dateToday = formatDate.parse(dateTodayStr);

        Date weekStartPlan = formatDate.parse(weekStart);
        List<MedicineRecord> planExist = medicineRecordRepository.findByAppUser(appUser.get().getId());
        // Lọc các bản ghi theo weekStart và lấy các ID loại thuốc không bị trùng
        Set<MedicineType> uniqueMedicineTypeIds = planExist
                .stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartPlan);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .map(MedicineRecord::getMedicineType)
                .collect(Collectors.toSet());

        List<MedicinePLanResponseDTO> medicinePLanResponseDTOList = new ArrayList<>();

        for (MedicineType type : uniqueMedicineTypeIds) {
            List<MedicineRecord> planPerMedicine = medicineRecordRepository.findByAppUser(appUser.get().getId())
                    .stream()
                    .filter(record -> {
                        String recordWeekStartStr = formatDate.format(record.getWeekStart());
                        try {
                            Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                            return recordWeekStart.equals(weekStartPlan)
                                    && record.getMedicineType().getId().equals(type.getId());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }).collect(Collectors.toList());
            MedicinePLanResponseDTO medicinePLanResponseDTO = new MedicinePLanResponseDTO();
            medicinePLanResponseDTO.setMedicineId(type.getId());
            medicinePLanResponseDTO.setMedicineTitle(type.getTitle());
            for (MedicineRecord record : planPerMedicine) {
                medicinePLanResponseDTO.setTime(getTime(record.getDate()));
                medicinePLanResponseDTO.getWeekday().add(getDayOfWeek(record.getDate()));
            }
            medicinePLanResponseDTOList.add(medicinePLanResponseDTO);
        }
        return medicinePLanResponseDTOList.stream()
                .sorted((record1, record2) -> record1.getMedicineId().compareTo(record2.getMedicineId()))
                .collect(Collectors.toList());

    }

    @Override
    public MedicineResponseChartDTO getDataChart() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);

        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);


        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(appUser.get().getId());
        //Sắp xếp giảm dần theo date
        medicineRecordList.sort(new Comparator<MedicineRecord>() {
            @Override
            public int compare(MedicineRecord recordDateSmaller, MedicineRecord recordDateBigger) {
                return recordDateBigger.getDate().compareTo(recordDateSmaller.getDate());
            }
        });
        MedicineResponseChartDTO medicineResponseChartDTO = new MedicineResponseChartDTO();
        List<MedicineResponse> medicineResponseList = new ArrayList<>();
        //Lấy ra 5 date gần nhất
        Set<Date> uniqueDates = new HashSet<>();
        for (MedicineRecord record : medicineRecordList) {
            String recordDateStr = formatDate.format(record.getDate());
            Date recordDate = formatDate.parse(recordDateStr);
            if(recordDate.before(date) || recordDate.equals(date)) {
                if(!uniqueDates.contains(recordDate)){
                    uniqueDates.add(recordDate);
                }
            }
            if(uniqueDates.size() == 5){
                break;
            }
        }
        //Sắp xếp date tăng dần
        Set<Date> sortedDates = new TreeSet<>(uniqueDates);
        //Tìm danh sách theo date
        for (Date sortedDate : sortedDates) {

            if(sortedDate.equals(date)){
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
            }else{
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
        return  medicineResponseChartDTO;
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
}
