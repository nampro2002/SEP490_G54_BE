package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.LessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.LessonService;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public Lesson createLesson(LessonRequestDTO lessonRequestDTO) {
        Lesson lesson = Lesson.builder()
                .title(lessonRequestDTO.getTitle())
                .content(lessonRequestDTO.getContent())
                .video(lessonRequestDTO.getVideo())
                .build();
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getLessonById(Integer id) {
        Optional<Lesson> lesson =  lessonRepository.findById(id);
        if (lesson.isEmpty()){
            throw new AppException(ErrorCode.LESSON_NOT_FOUND);
        }
        return lesson.get();
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson updateLesson(Integer id, LessonRequestDTO lesson) {
        Lesson lessonToUpdate = getLessonById(id);
        lessonToUpdate.setTitle(lesson.getTitle());
        lessonToUpdate.setContent(lesson.getContent());
        lessonToUpdate.setVideo(lesson.getVideo());
        return lessonRepository.save(lessonToUpdate);
    }

    @Override
    public Lesson deleteLesson(Integer id) {
        Lesson lesson = getLessonById(id);
        lessonRepository.deleteById(id);
        return lesson;
    }
}
