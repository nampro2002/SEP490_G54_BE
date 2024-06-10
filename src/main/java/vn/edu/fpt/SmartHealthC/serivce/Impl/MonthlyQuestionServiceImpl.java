package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyQuestionServiceImpl implements MonthlyQuestionService {

    @Autowired
    private MonthlyQuestionRepository monthlyQuestionRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public MonthlyQuestion createMonthlyQuestion(MonthlyQuestionDTO monthlyQuestionDTO)
    {
        MonthlyQuestion monthlyQuestion =  MonthlyQuestion.builder()
                .monthStart(monthlyQuestionDTO.getMonthStart())
                .isSAT(monthlyQuestionDTO.getIsSAT())
                .questionNumber(monthlyQuestionDTO.getQuestionNumber())
                .question(monthlyQuestionDTO.getQuestion())
                .answer(monthlyQuestionDTO.getAnswer())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(monthlyQuestionDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        monthlyQuestion.setAppUserId(appUser.get());
        return  monthlyQuestionRepository.save(monthlyQuestion);
    }

    @Override
    public MonthlyQuestion getMonthlyQuestionById(Integer id) {
        Optional<MonthlyQuestion> monthlyQuestion = monthlyQuestionRepository.findById(id);
        if(monthlyQuestion.isEmpty()) {
            throw new AppException(ErrorCode.MONTHLY_QUESTION_NOTFOUND);
        }

        return monthlyQuestion.get();
    }

    @Override
    public List<MonthlyQuestion> getAllMonthlyQuestions() {
        return monthlyQuestionRepository.findAll();
    }

    @Override
    public MonthlyQuestion updateMonthlyQuestion(Integer id,MonthlyQuestionDTO monthlyQuestionDTO) {

        MonthlyQuestion monthlyQuestion = getMonthlyQuestionById(id);
        monthlyQuestion.setMonthStart(monthlyQuestionDTO.getMonthStart());
        monthlyQuestion.setIsSAT(monthlyQuestionDTO.getIsSAT());
        monthlyQuestion.setQuestionNumber(monthlyQuestionDTO.getQuestionNumber());
        monthlyQuestion.setQuestion(monthlyQuestionDTO.getQuestion());
        monthlyQuestion.setAnswer(monthlyQuestionDTO.getAnswer());
        return  monthlyQuestionRepository.save(monthlyQuestion);
    }

    @Override
    public MonthlyQuestion deleteMonthlyQuestion(Integer id) {
        MonthlyQuestion monthlyQuestion = getMonthlyQuestionById(id);
        monthlyQuestionRepository.deleteById(id);
        return monthlyQuestion;
    }


}