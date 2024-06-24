package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MentalRecordServiceImpl implements MentalRecordService {

    @Autowired
    private MentalRecordRepository mentalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MentalRuleRepository mentalRuleRepository;
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
    public MentalRecordResponseDTO createMentalRecord(MentalRecordCreateDTO mentalRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);


        String weekStartStr= formatDate.format(mentalRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<MentalRecord> mentalRecordExist = mentalRecordRepository.findByAppUserId(appUser.getId());
        boolean dateExists = mentalRecordExist.stream()
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
            throw new AppException(ErrorCode.MENTAL_PLAN_EXIST);
        }

        int count=0;
        Date dateCalculate;
        while(true){
            if(count >= 7 ){
                break;
            }
            dateCalculate = calculateDate(mentalRecordDTO.getWeekStart(), count);
            for (Integer id : mentalRecordDTO.getMentalRuleId()) {
                MentalRecord mentalRecord =  MentalRecord.builder()
                        .status(false)
                        .weekStart(mentalRecordDTO.getWeekStart())
                        .date(dateCalculate)
                        .build();
                mentalRecord.setAppUserId(appUser);

                Optional<MentalRule> mentalRule = mentalRuleRepository.findById(id);
                if(mentalRule.isEmpty()) {
                    throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
                }
                mentalRecord.setMentalRule(mentalRule.get());
                mentalRecordRepository.save(mentalRecord);
            }
            count++;
        }


//        return MentalRecordResponseDTO.builder()
//                .appUserId(mentalRecord.getAppUserId().getId())
//                .status(mentalRecord.isStatus())
//                .weekStart(mentalRecord.getWeekStart())
//                .date(mentalRecord.getDate())
//                .mentalRuleId(mentalRecord.getMentalRule().getId())
//                .build();
        return null;
    }

    @Override
    public MentalRecord getMentalRecordEntityById(Integer id) {
        Optional<MentalRecord> mentalRecord = mentalRecordRepository.findById(id);
        if(mentalRecord.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_NOT_FOUND);
        }
        return mentalRecord.get();
    }
    @Override
    public MentalRecordResponseDTO getMentalRecordById(Integer id) {
        Optional<MentalRecord> mentalRecord = mentalRecordRepository.findById(id);
        if(mentalRecord.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_NOT_FOUND);
        }
        return MentalRecordResponseDTO.builder()
                .appUserId(mentalRecord.get().getAppUserId().getId())
                .status(mentalRecord.get().isStatus())
                .weekStart(mentalRecord.get().getWeekStart())
                .date(mentalRecord.get().getDate())
                .mentalRuleId(mentalRecord.get().getMentalRule().getId())
                .build();
    }


    @Override
    public List<MentalRecordListResDTO> getAllMentalRecords(Integer userId) {
        List<Date> mentalDate = mentalRecordRepository.findDistinctDate(userId);
        List<MentalRecordListResDTO> listResponseDTOList = new ArrayList<>();
        for (Date date : mentalDate) {
            listResponseDTOList.add(MentalRecordListResDTO.builder()
                    .date(date)
                    .build());
        }
        for (MentalRecordListResDTO record : listResponseDTOList) {
            List<MentalRecord> mentalRecords = mentalRecordRepository.findByAppUserIdAndDate(userId, record.getDate());
            record.setMentalRuleTitle(new ArrayList<>());
            int count = 0;
            for (MentalRecord mentalRecord : mentalRecords) {
                if (mentalRecord.isStatus()) {
                    record.getMentalRuleTitle().add(mentalRecord.getMentalRule().getTitle());
                    count++;
                }
            }
            record.setStatus(count+"/"+mentalRecords.size());
        }
        return listResponseDTOList;
    }

    @Override
    public MentalRecordResponseDTO updateMentalRecord(MentalRecordUpdateDTO mentalRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser appUser = appUserService.findAppUserByEmail(email);

        String dateStr= formatDate.format(mentalRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        boolean ruleExists = false;
        List<MentalRecord> planExist = mentalRecordRepository.findByAppUserId(appUser.getId());
        for (Integer rule : mentalRecordDTO.getMentalRuleId()){
            ruleExists = planExist.stream()
                    .anyMatch(record -> record.getMentalRule().getId().equals(rule));
        }
        if (!ruleExists) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }

        for (Integer rule : mentalRecordDTO.getMentalRuleId()){
            Optional<MentalRecord> mentalRecord = planExist.stream()
                    .filter(record -> {
                        String recordDateStr = formatDate.format(record.getDate());
                        try {
                            Date recordDate = formatDate.parse(recordDateStr);
                            return recordDate.equals(date)
                                    && record.getMentalRule().getId().equals(rule);
                        } catch (ParseException e) {
                            return false;
                        }
                    })
                    .findFirst();
            if (mentalRecord.isEmpty()) {
                throw new AppException(ErrorCode.MENTAL_RULE_IN_PLAN_NOT_FOUND);
            }
            MentalRecord mentalRecordUpdate =getMentalRecordEntityById(mentalRecord.get().getId());
            mentalRecordUpdate.setDate(mentalRecordDTO.getDate());
            mentalRecordUpdate.setStatus(mentalRecordDTO.getStatus());
            mentalRecordRepository.save(mentalRecordUpdate);
        }
        return null;
    }

    @Override
    public MentalRecordResponseDTO deleteMentalRecord(Integer id) {
        MentalRecord mentalRecord = getMentalRecordEntityById(id);
        mentalRecordRepository.deleteById(id);
        return MentalRecordResponseDTO.builder()
                .appUserId(mentalRecord.getAppUserId().getId())
                .status(mentalRecord.isStatus())
                .weekStart(mentalRecord.getWeekStart())
                .date(mentalRecord.getDate())
                .mentalRuleId(mentalRecord.getMentalRule().getId())
                .build();
    }
}