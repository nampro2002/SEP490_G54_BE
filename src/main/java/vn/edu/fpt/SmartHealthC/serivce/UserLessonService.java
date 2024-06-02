package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;

import java.util.List;
import java.util.Optional;

public interface UserLessonService {
    UserLesson createUserLesson(UserLesson userLesson);
    Optional<UserLesson> getUserLessonById(Integer id);
    List<UserLesson> getAllUserLessons();
    UserLesson updateUserLesson(UserLesson userLesson);
    void deleteUserLesson(Integer id);
}
