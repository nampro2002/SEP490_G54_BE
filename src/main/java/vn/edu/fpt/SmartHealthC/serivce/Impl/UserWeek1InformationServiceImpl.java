package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information.*;
import vn.edu.fpt.SmartHealthC.repository.UserWeek1InformationRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserWeek1InformationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserWeek1InformationServiceImpl implements UserWeek1InformationService {

    @Autowired
    private UserWeek1InformationRepository userWeek1InformationRepository;


    @Override
    public Lesson1DTO getLesson1(Integer id) {
        return null;
    }

    @Override
    public void setLesson1(Integer id, Lesson1DTO lesson1DTO) {

    }

    @Override
    public Lesson2DTO getLesson2(Integer id) {
        return null;
    }

    @Override
    public void setLesson2(Integer id, Lesson2DTO lesson2DTO) {

    }

    @Override
    public Lesson3DTO getLesson3(Integer id) {
        return null;
    }

    @Override
    public void setLesson3(Integer id, Lesson3DTO lesson3DTO) {

    }

    @Override
    public Lesson4DTO getLesson4(Integer id) {
        return null;
    }

    @Override
    public void setLesson4(Integer id, Lesson4DTO lesson4DTO) {

    }

    @Override
    public Lesson5DTO getLesson5(Integer id) {
        return null;
    }

    @Override
    public void setLesson5(Integer id, Lesson5DTO lesson5DTO) {

    }

    @Override
    public Lesson6DTO getLesson6(Integer id) {
        return null;
    }

    @Override
    public void setLesson6(Integer id, Lesson6DTO lesson6DTO) {

    }

    @Override
    public Lesson7DTO getLesson7(Integer id) {
        return null;
    }

    @Override
    public void setLesson7(Integer id, Lesson7DTO lesson7DTO) {

    }
}