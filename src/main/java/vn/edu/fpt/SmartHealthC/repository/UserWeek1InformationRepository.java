package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.UserWeek1Information;

import java.util.List;
import java.util.Optional;

public interface UserWeek1InformationRepository extends JpaRepository<UserWeek1Information, Integer> {

    @Query("SELECT u FROM user_week1_information u WHERE u.appUserId=?1")
    Optional<UserWeek1Information> findByAppUser(AppUser appUser);
}
