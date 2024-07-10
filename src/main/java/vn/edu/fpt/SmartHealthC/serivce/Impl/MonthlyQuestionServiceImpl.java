package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyAnswerResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyNumberResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MonthlyQuestionServiceImpl implements MonthlyQuestionService {

    @Autowired
    private MonthlyQuestionRepository monthlyQuestionRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;


    @Override
    public void createNewMonthMark(int appUserId) {
        Optional<AppUser> appUser = appUserRepository.findById(appUserId);
        Integer lastMonth = monthlyQuestionRepository.findByAppUser(appUser.get().getId());
        MonthlyRecord monthlyRecord = MonthlyRecord.builder()
                .appUserId(appUser.get())
                .monthNumber(lastMonth == null ? 1 : lastMonth+1)
                .monthlyRecordType(MonthlyRecordType.NEW_MONTH_MARK)
                .build();
        monthlyQuestionRepository.save(monthlyRecord);
    }

    @Override
    public void create40MonthlyQuestion(List<MonthlyQuestionDTO> monthlyQuestionDTO) {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        for (MonthlyQuestionDTO record : monthlyQuestionDTO) {
            MonthlyRecord monthlyRecord = MonthlyRecord.builder()
                    .appUserId(appUser)
                    .monthNumber(record.getMonthNumber())
                    .monthlyRecordType(record.getMonthlyRecordType())
                    .questionNumber(record.getQuestionNumber())
                    .question(record.getQuestion())
                    .answer(record.getAnswer())
                    .build();
            monthlyQuestionRepository.save(monthlyRecord);
        }
    }

    @Override
    public List<MonthlyNumberResponseDTO> getList3MonthlyNumber() {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        List<MonthlyNumberResponseDTO> monthlyNumberResponseDTOList = new ArrayList<>();
        List<Integer> monthlyNumbers = monthlyQuestionRepository.find3ByAppUser(appUser.getId());
        for (Integer monthNumber : monthlyNumbers) {
            boolean isAnswered= monthlyQuestionRepository.countMonthNumberByAppUser(appUser.getId(),monthNumber) > 1;
          MonthlyNumberResponseDTO monthlyNumberResponseDTO = new MonthlyNumberResponseDTO().builder()
                  .monthNumber(monthNumber)
                  .isAnswered(isAnswered)
                  .build();
            monthlyNumberResponseDTOList.add(monthlyNumberResponseDTO);
        }
        return monthlyNumberResponseDTOList;
    }

    @Override
    public List<MonthlyAnswerResponseDTO> getWebListAnswer(int userId, int monthNumber) {
        List<MonthlyAnswerResponseDTO> monthlyAnswerResponseDTOList = new ArrayList<>();
        List<MonthlyRecord> monthlyNumbers = monthlyQuestionRepository.findAllByAppUserAndMonthNumber(userId,monthNumber);
        for (MonthlyRecord record : monthlyNumbers) {
            MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                    .questionNumber(record.getQuestionNumber())
                    .question(record.getQuestion())
                    .type(record.getMonthlyRecordType())
                    .answer(record.getAnswer())
                    .build();
            monthlyAnswerResponseDTOList.add(monthlyNumberResponseDTO);
        }
        return monthlyAnswerResponseDTOList;
    }

    @Override
    public List<MonthlyAnswerResponseDTO> getMobileListAnswer(int monthNumber) {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        List<MonthlyAnswerResponseDTO> monthlyAnswerResponseDTOList = new ArrayList<>();
        List<MonthlyRecord> monthlyNumbers = monthlyQuestionRepository.findAllByAppUserAndMonthNumber(appUser.getId(),monthNumber);
        for (MonthlyRecord record : monthlyNumbers) {
            MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                    .questionNumber(record.getQuestionNumber())
                    .question(record.getQuestion())
                    .type(record.getMonthlyRecordType())
                    .answer(record.getAnswer())
                    .build();
            monthlyAnswerResponseDTOList.add(monthlyNumberResponseDTO);
        }
        return monthlyAnswerResponseDTOList;
    }


}