package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserLessonDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.LessonRepository;
import vn.edu.fpt.SmartHealthC.repository.UserLessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserLessonService;

import java.util.List;
import java.util.Optional;
@Service
public class UserLessonServiceImpl implements UserLessonService {

    @Autowired
    private UserLessonRepository userLessonRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public UserLesson createUserLesson(UserLessonDTO userLessonDTO) {

        UserLesson userLesson =  UserLesson.builder()
                .lessonDate(userLessonDTO.getLessonDate())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(userLessonDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Optional<Lesson> lesson = lessonRepository.findById(userLessonDTO.getLessonId());
        if(lesson.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        userLesson.setAppUserId(appUser.get());
        userLesson.setLessonId(lesson.get());
        return userLessonRepository.save(userLesson);
    }

    @Override
    public Optional<UserLesson> getUserLessonById(Integer id) {
        return userLessonRepository.findById(id);
    }

    @Override
    public List<UserLesson> getAllUserLessons() {
        return userLessonRepository.findAll();
    }

    @Override
    public UserLesson updateUserLesson(UserLessonDTO userLessonDTO) {

        UserLesson userLesson =  UserLesson.builder()
                .Id(userLessonDTO.getId())
                .lessonDate(userLessonDTO.getLessonDate())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(userLessonDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Optional<Lesson> lesson = lessonRepository.findById(userLessonDTO.getLessonId());
        if(lesson.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        userLesson.setAppUserId(appUser.get());
        userLesson.setLessonId(lesson.get());
        return userLessonRepository.save(userLesson);
    }

    @Override
    public void deleteUserLesson(Integer id) {
        userLessonRepository.deleteById(id);
    }
}
