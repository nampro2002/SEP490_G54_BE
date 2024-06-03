package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}