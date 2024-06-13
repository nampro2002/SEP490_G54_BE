package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;

public interface WebUserRepository extends JpaRepository<WebUser, Integer> {

}
