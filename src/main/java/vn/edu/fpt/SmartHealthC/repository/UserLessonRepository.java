package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;

public interface UserLessonRepository extends JpaRepository<UserLesson, Integer> {
}

