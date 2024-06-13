package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;

import java.util.Date;
import java.util.List;

public interface MedicineRecordRepository extends JpaRepository<MedicineRecord, Integer> {
    @Query("SELECT DISTINCT m.date FROM MedicineRecord m WHERE m.appUserId.id = ?1")
    List<Date> findDistinctDate(Integer userId);
    @Query("SELECT m FROM MedicineRecord m WHERE m.date = ?1 AND m.appUserId.id = ?2")
    List<MedicineRecord> findByDate(Date date, Integer userId);
}
