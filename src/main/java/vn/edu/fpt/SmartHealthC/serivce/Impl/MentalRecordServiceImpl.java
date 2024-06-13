package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MentalRecordServiceImpl implements MentalRecordService {

    @Autowired
    private MentalRecordRepository mentalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MentalRuleRepository mentalRuleRepository;

    @Override
    public MentalRecordResponseDTO createMentalRecord(MentalRecordDTO mentalRecordDTO) {
        MentalRecord mentalRecord =  MentalRecord.builder()
                .status(false)
                .weekStart(mentalRecordDTO.getWeekStart())
                .date(mentalRecordDTO.getDate())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(mentalRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        mentalRecord.setAppUserId(appUser.get());
        Optional<MentalRule> mentalRule = mentalRuleRepository.findById(mentalRecordDTO.getMentalRuleId());
        if(mentalRule.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }
        mentalRecord.setMentalRule(mentalRule.get());
        mentalRecord =  mentalRecordRepository.save(mentalRecord);
        return MentalRecordResponseDTO.builder()
                .appUserId(mentalRecord.getAppUserId().getId())
                .status(mentalRecord.isStatus())
                .weekStart(mentalRecord.getWeekStart())
                .date(mentalRecord.getDate())
                .mentalRuleId(mentalRecord.getMentalRule().getId())
                .build();
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
    public MentalRecordResponseDTO updateMentalRecord(Integer id,MentalRecordDTO mentalRecordDTO) {
        MentalRecord mentalRecord =  getMentalRecordEntityById(id);
        mentalRecord.setStatus(mentalRecordDTO.isStatus());
        mentalRecord.setWeekStart(mentalRecordDTO.getWeekStart());
        mentalRecord.setDate(mentalRecordDTO.getDate());
        mentalRecord = mentalRecordRepository.save(mentalRecord);
        return MentalRecordResponseDTO.builder()
                .appUserId(mentalRecord.getAppUserId().getId())
                .status(mentalRecord.isStatus())
                .weekStart(mentalRecord.getWeekStart())
                .date(mentalRecord.getDate())
                .mentalRuleId(mentalRecord.getMentalRule().getId())
                .build();
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