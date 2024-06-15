package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyQuestionServiceImpl implements MonthlyQuestionService {

    @Autowired
    private MonthlyQuestionRepository monthlyQuestionRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public MonthlyRecord createMonthlyQuestion(MonthlyQuestionDTO monthlyQuestionDTO) {
        MonthlyRecord monthlyRecord = MonthlyRecord.builder()
                .monthStart(monthlyQuestionDTO.getMonthStart())
                .monthlyRecordType(monthlyQuestionDTO.getMonthlyRecordType())
                .questionNumber(monthlyQuestionDTO.getQuestionNumber())
                .question(monthlyQuestionDTO.getQuestion())
                .answer(monthlyQuestionDTO.getAnswer())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(monthlyQuestionDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        monthlyRecord.setAppUserId(appUser.get());
        return monthlyQuestionRepository.save(monthlyRecord);
    }

    @Override
    public MonthlyRecord getMonthlyQuestionById(Integer id) {
        Optional<MonthlyRecord> monthlyQuestion = monthlyQuestionRepository.findById(id);
        if (monthlyQuestion.isEmpty()) {
            throw new AppException(ErrorCode.MONTHLY_QUESTION_NOTFOUND);
        }

        return monthlyQuestion.get();
    }

    @Override
    public List<MonthlyRecord> getAllMonthlyQuestionsMobile() {
        return monthlyQuestionRepository.findAll();
    }

    @Override
    public MonthlyRecord updateMonthlyQuestion(Integer id, MonthlyQuestionDTO monthlyQuestionDTO) {

        MonthlyRecord monthlyRecord = getMonthlyQuestionById(id);
        monthlyRecord.setMonthStart(monthlyQuestionDTO.getMonthStart());
        monthlyRecord.setMonthlyRecordType(monthlyQuestionDTO.getMonthlyRecordType());
        monthlyRecord.setQuestionNumber(monthlyQuestionDTO.getQuestionNumber());
        monthlyRecord.setQuestion(monthlyQuestionDTO.getQuestion());
        monthlyRecord.setAnswer(monthlyQuestionDTO.getAnswer());
        return monthlyQuestionRepository.save(monthlyRecord);
    }

    @Override
    public MonthlyRecord deleteMonthlyQuestion(Integer id) {
        MonthlyRecord monthlyRecord = getMonthlyQuestionById(id);
        monthlyQuestionRepository.deleteById(id);
        return monthlyRecord;
    }


}