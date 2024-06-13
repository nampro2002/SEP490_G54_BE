package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.LessonResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.LessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.LessonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public LessonResponseDTO createLesson(LessonRequestDTO lessonRequestDTO) {
        Lesson lesson = Lesson.builder()
                .title(lessonRequestDTO.getTitle())
                .content(lessonRequestDTO.getContent())
                .video(lessonRequestDTO.getVideo())
                .build();
        lesson = lessonRepository.save(lesson);
        LessonResponseDTO lessonResponseDTO = LessonResponseDTO
                .builder()
                .title(lesson.getTitle())
                .content(lessonRequestDTO.getContent())
                .video(lesson.getVideo())
                .build();
        return lessonResponseDTO;
    }

    @Override
    public LessonResponseDTO getLessonById(Integer id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isEmpty()) {
            throw new AppException(ErrorCode.LESSON_NOT_FOUND);
        }
        LessonResponseDTO lessonResponseDTO = LessonResponseDTO
                .builder()
                .title(lesson.get().getTitle())
                .content(lesson.get().getContent())
                .video(lesson.get().getVideo())
                .build();
        return lessonResponseDTO;
    }


    @Override
    public Lesson getLessonEntityById(Integer id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isEmpty()) {
            throw new AppException(ErrorCode.LESSON_NOT_FOUND);
        }
        return lesson.get();
    }

    @Override
    public List<LessonResponseDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        List<LessonResponseDTO> lessonResponseDTOList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            LessonResponseDTO lessonResponseDTO = LessonResponseDTO
                    .builder()
                    .title(lesson.getTitle())
                    .content(lesson.getContent())
                    .video(lesson.getVideo())
                    .build();
            lessonResponseDTOList.add(lessonResponseDTO);
        }
        return lessonResponseDTOList;
    }

    @Override
    public LessonResponseDTO updateLesson(Integer id, LessonRequestDTO lesson) {
        Lesson lessonToUpdate = getLessonEntityById(id);
        lessonToUpdate.setTitle(lesson.getTitle());
        lessonToUpdate.setContent(lesson.getContent());
        lessonToUpdate.setVideo(lesson.getVideo());
        lessonToUpdate = lessonRepository.save(lessonToUpdate);
        LessonResponseDTO lessonResponseDTO = LessonResponseDTO
                .builder()
                .title(lessonToUpdate.getTitle())
                .content(lessonToUpdate.getContent())
                .video(lessonToUpdate.getVideo())
                .build();

        return lessonResponseDTO;
    }

    @Override
    public LessonResponseDTO deleteLesson(Integer id) {
        Lesson lesson = getLessonEntityById(id);
        lessonRepository.deleteById(id);
        LessonResponseDTO lessonResponseDTO = LessonResponseDTO
                .builder()
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .video(lesson.getVideo())
                .build();
        return lessonResponseDTO;
    }
}
