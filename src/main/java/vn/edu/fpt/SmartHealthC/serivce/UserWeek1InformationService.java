package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information.*;

public interface UserWeek1InformationService {
    Lesson1DTO getLesson1(Integer id);
    void setLesson1(Integer id, Lesson1DTO lesson1DTO);

    Lesson2DTO getLesson2(Integer id);
    void setLesson2(Integer id, Lesson2DTO lesson2DTO);

    Lesson3DTO getLesson3(Integer id);
    void setLesson3(Integer id, Lesson3DTO lesson3DTO) ;

    Lesson4DTO getLesson4(Integer id);
    void setLesson4(Integer id, Lesson4DTO lesson4DTO) ;

    Lesson5DTO getLesson5(Integer id);
    void setLesson5(Integer id, Lesson5DTO lesson5DTO) ;

    Lesson6DTO getLesson6(Integer id);
    void setLesson6(Integer id, Lesson6DTO lesson6DTO) ;

    Lesson7DTO getLesson7(Integer id);
    void setLesson7(Integer id, Lesson7DTO lesson7DTO) ;

}