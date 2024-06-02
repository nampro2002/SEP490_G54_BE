package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, Integer> {
}

