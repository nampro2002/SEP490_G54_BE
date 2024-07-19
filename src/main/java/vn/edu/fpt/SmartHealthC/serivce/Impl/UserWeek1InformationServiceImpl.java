package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information.*;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.domain.entity.UserWeek1Information;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.UserLessonRepository;
import vn.edu.fpt.SmartHealthC.repository.UserWeek1InformationRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserWeek1InformationService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserWeek1InformationServiceImpl implements UserWeek1InformationService {

    @Autowired
    private UserWeek1InformationRepository userWeek1InformationRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserLessonRepository userLessonRepository;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Override
    public UserWeek1Information getUserWeek1Information() {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserWeek1Information> userWeek1Information = userWeek1InformationRepository.findByAppUser(appUser);
        if(userWeek1Information.isEmpty()) {
            throw new AppException(ErrorCode.USER_WEEK1_LESSON1_NOT_FOUND);
        }
        return userWeek1Information.get();
    }

    @Override
    public Lesson1DTO getLesson1() {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        Lesson1DTO lesson1DTO = new Lesson1DTO().builder()
                .endOfYearGoal(userWeek1Information.getEndOfYearGoal())
                .intermediateGoal(userWeek1Information.getIntermediateGoal()).build();
        return lesson1DTO;
    }

    @Override
    public void setLesson1( Lesson1DTO lesson1DTO) throws ParseException {

        UserWeek1Information userWeek1Information = getUserWeek1Information();
        userWeek1Information.setEndOfYearGoal(lesson1DTO.getEndOfYearGoal());
        userWeek1Information.setIntermediateGoal(lesson1DTO.getIntermediateGoal());


        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);

        if(userLesson.isEmpty()){
            UserLesson userLesson1 = new UserLesson();
            userLesson1.setAppUserId(appUser);
            userLesson1.setLesson(1);
            userLesson1.setLessonDate(DateUtils.getToday(simpleDateFormat));
            userLessonRepository.save(userLesson1);
        }
        userWeek1InformationRepository.save(userWeek1Information);
    }

    @Override
    public Lesson2DTO getLesson2() {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        Lesson2DTO lesson2DTO = new Lesson2DTO().builder()
                .strength(userWeek1Information.getStrength())
                .weakPoint(userWeek1Information.getWeakPoint()).build();
        return lesson2DTO;
    }

    @Override
    public void setLesson2( Lesson2DTO lesson2DTO) throws ParseException {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        userWeek1Information.setStrength(lesson2DTO.getStrength());
        userWeek1Information.setWeakPoint(lesson2DTO.getWeakPoint());

        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);
        if(userLesson.isPresent()){
            UserLesson userLesson2 = userLesson.get();
            userLesson2.setAppUserId(appUser);
            if(userLesson.get().getLesson() <2){
                userLesson2.setLesson(2);
                userLesson2.setLessonDate(DateUtils.getToday(simpleDateFormat));
            }
            userLessonRepository.save(userLesson2);
        }else{
            throw new AppException(ErrorCode.USER_LESSON_NOT_FOUND);
        }

        userWeek1InformationRepository.save(userWeek1Information);
    }

    @Override
    public Lesson3DTO getLesson3() {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        Lesson3DTO lesson3DTO = new Lesson3DTO().builder()
                .closePerson1(userWeek1Information.getClosePerson1())
                .closePerson2(userWeek1Information.getClosePerson2())
                .closePerson1Message(userWeek1Information.getClosePerson1Message())
                .closePerson2Message(userWeek1Information.getClosePerson2Message())
                .prefrerredEnvironment(userWeek1Information.getPrefrerredEnvironment())
                .prefrerredTime(userWeek1Information.getPrefrerredTime())
                .notPreferredLocation(userWeek1Information.getNotPreferredLocation())
                .notPreferredTime(userWeek1Information.getNotPreferredTime()).build();
        return lesson3DTO;
    }

    @Override
    public void setLesson3(Lesson3DTO lesson3DTO) throws ParseException {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        userWeek1Information.setClosePerson1(lesson3DTO.getClosePerson1());
        userWeek1Information.setClosePerson2(lesson3DTO.getClosePerson2());
        userWeek1Information.setClosePerson1Message(lesson3DTO.getClosePerson1Message());
        userWeek1Information.setClosePerson2Message(lesson3DTO.getClosePerson2Message());
        userWeek1Information.setPrefrerredEnvironment(lesson3DTO.getPrefrerredEnvironment());
        userWeek1Information.setPrefrerredTime(lesson3DTO.getPrefrerredTime());
        userWeek1Information.setNotPreferredLocation(lesson3DTO.getNotPreferredLocation());
        userWeek1Information.setNotPreferredTime(lesson3DTO.getNotPreferredTime());
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);
        if(userLesson.isPresent()){
            UserLesson userLesson2 = userLesson.get();
            userLesson2.setAppUserId(appUser);
            if(userLesson.get().getLesson() <3){
                userLesson2.setLesson(3);
                userLesson2.setLessonDate(DateUtils.getToday(simpleDateFormat));
            }
            userLessonRepository.save(userLesson2);
        }else{
            throw new AppException(ErrorCode.USER_LESSON_NOT_FOUND);
        }
        userWeek1InformationRepository.save(userWeek1Information);
    }

    @Override
    public Lesson4DTO getLesson4() {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        Lesson4DTO lesson4DTO = new Lesson4DTO().builder()
                .score10(userWeek1Information.getScore10())
                .score20(userWeek1Information.getScore20())
                .score30(userWeek1Information.getScore30())
                .score40(userWeek1Information.getScore40())
                .score50(userWeek1Information.getScore50())
                .recentValues(userWeek1Information.getRecentValues())
                .influenceOnLife(userWeek1Information.getInfluenceOnLife())
                .newValues(userWeek1Information.getNewValues())
                .reasonForChanging(userWeek1Information.getReasonForChanging()).build();
        return lesson4DTO;
    }

    @Override
    public void setLesson4( Lesson4DTO lesson4DTO) throws ParseException {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        userWeek1Information.setScore10(lesson4DTO.getScore10());
        userWeek1Information.setScore20(lesson4DTO.getScore20());
        userWeek1Information.setScore30(lesson4DTO.getScore30());
        userWeek1Information.setScore40(lesson4DTO.getScore40());
        userWeek1Information.setScore50(lesson4DTO.getScore50());
        userWeek1Information.setRecentValues(lesson4DTO.getRecentValues());
        userWeek1Information.setInfluenceOnLife(lesson4DTO.getInfluenceOnLife());
        userWeek1Information.setNewValues(lesson4DTO.getNewValues());
        userWeek1Information.setReasonForChanging(lesson4DTO.getReasonForChanging());

        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);
        if(userLesson.isPresent()){
            UserLesson userLesson2 = userLesson.get();
            userLesson2.setAppUserId(appUser);
            if(userLesson.get().getLesson() <4){
                userLesson2.setLesson(4);
                userLesson2.setLessonDate(DateUtils.getToday(simpleDateFormat));
            }
            userLessonRepository.save(userLesson2);
        }else{
            throw new AppException(ErrorCode.USER_LESSON_NOT_FOUND);
        }

        userWeek1InformationRepository.save(userWeek1Information);
    }

    @Override
    public Lesson5DTO getLesson5() {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        Lesson5DTO lesson5DTO = new Lesson5DTO().builder()
                .currentEmotion(userWeek1Information.getCurrentEmotion())
                .whyIfRealistic(userWeek1Information.getWhyIfRealistic())
                .whyIfNotBetterForLife(userWeek1Information.getWhyIfNotBetterForLife()).build();
        return lesson5DTO;
    }

    @Override
    public void setLesson5(Lesson5DTO lesson5DTO) throws ParseException {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        userWeek1Information.setCurrentEmotion(lesson5DTO.getCurrentEmotion());
        userWeek1Information.setWhyIfRealistic(lesson5DTO.getWhyIfRealistic());
        userWeek1Information.setWhyIfNotBetterForLife(lesson5DTO.getWhyIfNotBetterForLife());

        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);
        if(userLesson.isPresent()){
            UserLesson userLesson2 = userLesson.get();
            userLesson2.setAppUserId(appUser);
            if(userLesson.get().getLesson() <5){
                userLesson2.setLesson(5);
                userLesson2.setLessonDate(DateUtils.getToday(simpleDateFormat));
            }
            userLessonRepository.save(userLesson2);
        }else{
            throw new AppException(ErrorCode.USER_LESSON_NOT_FOUND);
        }

        userWeek1InformationRepository.save(userWeek1Information);
    }

    @Override
    public Lesson6DTO getLesson6() {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        Lesson6DTO lesson6DTO = new Lesson6DTO().builder()
                .noMoreThan2(userWeek1Information.getNoMoreThan2())
                .todoList(userWeek1Information.getTodoList())
                .noProcastinating(userWeek1Information.getNoProcastinating())
                .doExercises(userWeek1Information.getDoExercises()).build();
        return lesson6DTO;
    }

    @Override
    public void setLesson6( Lesson6DTO lesson6DTO) throws ParseException {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        userWeek1Information.setNoMoreThan2(lesson6DTO.getNoMoreThan2());
        userWeek1Information.setTodoList(lesson6DTO.getTodoList());
        userWeek1Information.setNoProcastinating(lesson6DTO.getNoProcastinating());
        userWeek1Information.setDoExercises(lesson6DTO.getDoExercises());

        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);
        if(userLesson.isPresent()){
            UserLesson userLesson2 = userLesson.get();
            userLesson2.setAppUserId(appUser);
            if(userLesson.get().getLesson() <6){
                userLesson2.setLesson(6);
                userLesson2.setLessonDate(DateUtils.getToday(simpleDateFormat));
            }
            userLessonRepository.save(userLesson2);
        }else{
            throw new AppException(ErrorCode.USER_LESSON_NOT_FOUND);
        }

        userWeek1InformationRepository.save(userWeek1Information);
    }

    @Override
    public Lesson7DTO getLesson7() {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        Lesson7DTO lesson7DTO = new Lesson7DTO().builder()
                .whatIsHealth(userWeek1Information.getWhatIsHealth())
                .activityCommitment(userWeek1Information.getActivityCommitment())
                .dietCommitment(userWeek1Information.getDietCommitment())
                .mentalCommitment(userWeek1Information.getMentalCommitment())
                .medicineCommitment(userWeek1Information.getMentalCommitment())
                .roadBlock(userWeek1Information.getRoadBlock())
                .solution(userWeek1Information.getSolution())
                .commitment(userWeek1Information.getCommitment()).build();
        return lesson7DTO;
    }

    @Override
    public void setLesson7( Lesson7DTO lesson7DTO) throws ParseException {
        UserWeek1Information userWeek1Information = getUserWeek1Information();
        userWeek1Information.setWhatIsHealth(lesson7DTO.getWhatIsHealth());
        userWeek1Information.setActivityCommitment(lesson7DTO.getActivityCommitment());
        userWeek1Information.setDietCommitment(lesson7DTO.getDietCommitment());
        userWeek1Information.setMentalCommitment(lesson7DTO.getMentalCommitment());
        userWeek1Information.setMedicineCommitment(lesson7DTO.getMentalCommitment());
        userWeek1Information.setRoadBlock(lesson7DTO.getRoadBlock());
        userWeek1Information.setSolution(lesson7DTO.getSolution());
        userWeek1Information.setCommitment(lesson7DTO.getCommitment());

        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);
        if(userLesson.isPresent()){
            UserLesson userLesson2 = userLesson.get();
            userLesson2.setAppUserId(appUser);
            if(userLesson.get().getLesson() <7){
                userLesson2.setLesson(7);
                userLesson2.setLessonDate(DateUtils.getToday(simpleDateFormat));
            }
            userLessonRepository.save(userLesson2);
        }else{
            throw new AppException(ErrorCode.USER_LESSON_NOT_FOUND);
        }

        userWeek1InformationRepository.save(userWeek1Information);
    }

//    @Override
//    public Integer getUnlockedLessons() throws ParseException {
//        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
//
//        Optional<UserLesson> userLesson = userLessonRepository.findByAppUser(appUser);
//        if(userLesson.isEmpty()){
//            return 1;
//        }
//        Date lastDate = userLesson.get().getLessonDate();
//        Date today = DateUtils.getToday(simpleDateFormat);
//        if(today.after(lastDate)){
//            return userLesson.get().getLesson() < 7 ?userLesson.get().getLesson() +1:
//                    userLesson.get().getLesson();
//        }
//        return Math.min(userLesson.get().getLesson(), 7);
//    }
}