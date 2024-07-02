package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.UserWeek1Information;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;

import java.util.Date;
import java.util.List;

public interface UserWeek1InformationRepository extends JpaRepository<UserWeek1Information, Integer> {
}
