package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information.*;
import vn.edu.fpt.SmartHealthC.domain.entity.UserWeek1Information;

import java.text.ParseException;

public interface UserWeek1InformationService {
    UserWeek1Information getUserWeek1Information();
    Lesson1DTO getLesson1();
    void setLesson1( Lesson1DTO lesson1DTO) throws ParseException;

    Lesson2DTO getLesson2();
    void setLesson2( Lesson2DTO lesson2DTO) throws ParseException;

    Lesson3DTO getLesson3();
    void setLesson3( Lesson3DTO lesson3DTO) throws ParseException;

    Lesson4DTO getLesson4();
    void setLesson4( Lesson4DTO lesson4DTO) throws ParseException;

    Lesson5DTO getLesson5();
    void setLesson5( Lesson5DTO lesson5DTO) throws ParseException;

    Lesson6DTO getLesson6();
    void setLesson6( Lesson6DTO lesson6DTO) throws ParseException;

    Lesson7DTO getLesson7();
    void setLesson7( Lesson7DTO lesson7DTO) throws ParseException;

}