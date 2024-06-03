package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.UserChronicDisease;

public interface UserChronicDiseaseRepository extends JpaRepository<UserChronicDisease, Integer> {
}
