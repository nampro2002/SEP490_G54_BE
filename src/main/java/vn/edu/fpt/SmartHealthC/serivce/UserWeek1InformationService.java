package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information.*;
import vn.edu.fpt.SmartHealthC.domain.entity.UserWeek1Information;

public interface UserWeek1InformationService {
    UserWeek1Information getUserWeek1Information();
    Lesson1DTO getLesson1();
    void setLesson1( Lesson1DTO lesson1DTO);

    Lesson2DTO getLesson2();
    void setLesson2( Lesson2DTO lesson2DTO);

    Lesson3DTO getLesson3();
    void setLesson3( Lesson3DTO lesson3DTO) ;

    Lesson4DTO getLesson4();
    void setLesson4( Lesson4DTO lesson4DTO) ;

    Lesson5DTO getLesson5();
    void setLesson5( Lesson5DTO lesson5DTO) ;

    Lesson6DTO getLesson6();
    void setLesson6( Lesson6DTO lesson6DTO) ;

    Lesson7DTO getLesson7();
    void setLesson7( Lesson7DTO lesson7DTO) ;

}