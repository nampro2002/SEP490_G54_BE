package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserLessonDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.UserLessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
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
    private AppUserService appUserService;

    @Override
    public UserLesson createUserLesson(UserLessonDTO userLessonDTO) {

        UserLesson userLesson = UserLesson.builder()
                .lessonDate(userLessonDTO.getLessonDate())
                .build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);
//        Optional<Lesson> lesson = lessonRepository.findById(userLessonDTO.getLessonId());
//        if (lesson.isEmpty()) {
//            throw new AppException(ErrorCode.LESSON_NOT_FOUND);
//        }
        userLesson.setAppUserId(appUser);
//        userLesson.setLessonId(lesson.get());
        return userLessonRepository.save(userLesson);
    }

    @Override
    public UserLesson getUserLessonById(Integer id) {
        Optional<UserLesson> userLesson = userLessonRepository.findById(id);
        if (userLesson.isEmpty()) throw new AppException(ErrorCode.USER_LESSON_NOT_FOUND);
        return userLesson.get();
    }

    @Override
    public List<UserLesson> getAllUserLessons() {
        return userLessonRepository.findAll();
    }

    // cái này chắc k update
    @Override
    public UserLesson updateUserLesson(Integer id, UserLessonDTO userLessonDTO) {
        UserLesson userLesson = getUserLessonById(id);
        userLesson.setLessonDate(userLessonDTO.getLessonDate());
        return userLessonRepository.save(userLesson);
    }

    @Override
    public UserLesson deleteUserLesson(Integer id) {
        UserLesson userLesson = getUserLessonById(id);
        userLessonRepository.deleteById(id);
        return userLesson;
    }
}
