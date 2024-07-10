package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.domain.entity.UserWeek1Information;

import java.util.Optional;

public interface UserLessonRepository extends JpaRepository<UserLesson, Integer> {

    @Query("SELECT u FROM UserLesson u WHERE u.appUserId=?1")
    Optional<UserLesson> findByAppUser(AppUser appUser);
}

