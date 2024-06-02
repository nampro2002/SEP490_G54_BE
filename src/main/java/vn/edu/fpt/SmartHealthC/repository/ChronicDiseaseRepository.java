package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.SmartHealthC.domain.entity.ChronicDisease;

public interface ChronicDiseaseRepository extends JpaRepository<ChronicDisease, Integer> {
}