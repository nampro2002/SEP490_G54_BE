package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.LessonResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
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
                .lessonNumber(lessonRequestDTO.getLessonNumber())
                .build();
        lesson = lessonRepository.save(lesson);
        LessonResponseDTO lessonResponseDTO = LessonResponseDTO
                .builder()
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .video(lesson.getVideo())
                .lessonNumber(lesson.getLessonNumber())
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
                .lessonNumber(lesson.get().getLessonNumber())
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
    public ResponsePaging<List<LessonResponseDTO>> getAllLessons(Integer pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<Lesson> pagedResult = lessonRepository.findAll(paging, search);
        List<Lesson> lessons = new ArrayList<>();
        if (pagedResult.hasContent()) {
            lessons = pagedResult.getContent();
        }
        List<LessonResponseDTO> lessonResponseDTOList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            LessonResponseDTO lessonResponseDTO = LessonResponseDTO
                    .builder()
                    .id(lesson.getId())
                    .title(lesson.getTitle())
                    .content(lesson.getContent())
                    .video(lesson.getVideo())
                    .lessonNumber(lesson.getLessonNumber())
                    .build();
            lessonResponseDTOList.add(lessonResponseDTO);
        }
        return ResponsePaging.<List<LessonResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(lessonResponseDTOList)
                .build();
    }

    @Override
    public LessonResponseDTO updateLesson(Integer id, LessonRequestDTO lesson) {
        Lesson lessonToUpdate = getLessonEntityById(id);
        lessonToUpdate.setTitle(lesson.getTitle());
        lessonToUpdate.setContent(lesson.getContent());
        lessonToUpdate.setVideo(lesson.getVideo());
        lessonToUpdate = lessonRepository.save(lessonToUpdate);
        return LessonResponseDTO
                .builder()
                .title(lessonToUpdate.getTitle())
                .content(lessonToUpdate.getContent())
                .video(lessonToUpdate.getVideo())
                .lessonNumber(lessonToUpdate.getLessonNumber())
                .build();
    }

    @Override
    public LessonResponseDTO deleteLesson(Integer id) {
        Lesson lesson = getLessonEntityById(id);
        lessonRepository.deleteById(id);
        return LessonResponseDTO
                .builder()
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .video(lesson.getVideo())
                .lessonNumber(lesson.getLessonNumber())
                .build();
    }
}
