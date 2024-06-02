package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.repository.UserLessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserLessonService;

import java.util.List;
import java.util.Optional;
@Service
public class UserLessonServiceImpl implements UserLessonService {

    @Autowired
    private UserLessonRepository userLessonRepository;

    @Override
    public UserLesson createUserLesson(UserLesson userLesson) {
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
    public UserLesson updateUserLesson(UserLesson userLesson) {
        return userLessonRepository.save(userLesson);
    }

    @Override
    public void deleteUserLesson(Integer id) {
        userLessonRepository.deleteById(id);
    }
}
