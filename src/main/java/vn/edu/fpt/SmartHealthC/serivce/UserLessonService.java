package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.UserLessonDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.UnlockedLesson;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface UserLessonService {
    UserLesson createUserLesson(UserLessonDTO userLessonDTO);
    UserLesson getUserLessonById(Integer id);
    List<UserLesson> getAllUserLessons();
    UserLesson updateUserLesson(Integer id,UserLessonDTO userLessonDTO);
    UserLesson deleteUserLesson(Integer id);
    UnlockedLesson getUnlockedLessons() throws ParseException;

}
