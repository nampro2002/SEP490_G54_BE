package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordUpdateDTO;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Override
    public MedicineRecordResponseDTO createMedicineRecord(MedicineRecordCreateDTO medicineRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);
        Optional<MedicineType> medicineType = medicineTypeRepository.findById(medicineRecordDTO.getMedicineTypeId());
        if (medicineType.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }
        List<MedicineRecord> medicinePlanExist = medicineRecordRepository.
        findByWeekStartMedicineAppUser(
                medicineRecordDTO.getWeekStart(),
                appUser.getId(),
                medicineType.get().getId()
                );
        if (!medicinePlanExist.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_PLAN_EXIST);
        }
        Date dateCalculate;
        Map<String , Integer> dayIndexMap = new HashMap<>();
        dayIndexMap.put("MONDAY", 0);
        dayIndexMap.put("TUESDAY", 1);
        dayIndexMap.put("WEDNESDAY", 2);
        dayIndexMap.put("THURSDAY", 3);
        dayIndexMap.put("FRIDAY", 4);
        dayIndexMap.put("SATURDAY", 5);
        dayIndexMap.put("SUNDAY", 6);
        int count = 0;
        //Check schedule
        for(String date : medicineRecordDTO.getSchedule()){

            MedicineRecord medicineRecord = MedicineRecord.builder()
                    .weekStart(medicineRecordDTO.getWeekStart())
                    .status(false)
                    .build();
            medicineRecord.setAppUserId(appUser);
            medicineRecord.setMedicineType(medicineType.get());
            if (dayIndexMap.containsKey(date)) {
                dateCalculate = calculateDate(medicineRecord.getWeekStart(), dayIndexMap.get(date));
                medicineRecord.setDate(dateCalculate);
                medicineRecordRepository.save(medicineRecord);
            }
        }
        return null;
    }
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
    public MedicineRecordResponseDTO updateMedicineRecord(MedicineRecordUpdateDTO medicineRecordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);
        for (Integer type : medicineRecordDTO.getMedicineTypeId()){
            Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findByDateAndMedicine(
                    medicineRecordDTO.getDate(),appUser.getId(),type);
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
}
