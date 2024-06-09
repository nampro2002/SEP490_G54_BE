package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    Lesson createLesson(Lesson lesson);
    Lesson getLessonById(Integer id);
    List<Lesson> getAllLessons();
    Lesson updateLesson(Lesson lesson);
    Lesson deleteLesson(Integer id);
}
