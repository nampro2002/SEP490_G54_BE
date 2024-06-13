package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.LessonResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    LessonResponseDTO createLesson(LessonRequestDTO lesson);
    LessonResponseDTO getLessonById(Integer id);
    Lesson getLessonEntityById(Integer id);
    ResponsePaging<List<LessonResponseDTO>> getAllLessons(Integer pageNo, String search);
    LessonResponseDTO updateLesson(Integer id, LessonRequestDTO lesson);
    LessonResponseDTO deleteLesson(Integer id);
}
