package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
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
        AppUser appUser = appUserRepository.findById(monthlyQuestionDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        monthlyQuestion.setAppUserId(appUser);
        return  monthlyQuestionRepository.save(monthlyQuestion);
    }

    @Override
    public Optional<MonthlyQuestion> getMonthlyQuestionById(Integer id) {
        return monthlyQuestionRepository.findById(id);
    }

    @Override
    public List<MonthlyQuestion> getAllMonthlyQuestions() {
        return monthlyQuestionRepository.findAll();
    }

    @Override
    public MonthlyQuestion updateMonthlyQuestion(MonthlyQuestionDTO monthlyQuestionDTO) {
        MonthlyQuestion monthlyQuestion =  MonthlyQuestion.builder()
                .Id(monthlyQuestionDTO.getId())
                .monthStart(monthlyQuestionDTO.getMonthStart())
                .isSAT(monthlyQuestionDTO.getIsSAT())
                .questionNumber(monthlyQuestionDTO.getQuestionNumber())
                .question(monthlyQuestionDTO.getQuestion())
                .answer(monthlyQuestionDTO.getAnswer())
                .build();
        AppUser appUser = appUserRepository.findById(monthlyQuestionDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        monthlyQuestion.setAppUserId(appUser);
        return  monthlyQuestionRepository.save(monthlyQuestion);
    }

    @Override
    public void deleteMonthlyQuestion(Integer id) {
        monthlyQuestionRepository.deleteById(id);
    }


}