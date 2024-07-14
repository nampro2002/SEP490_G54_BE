package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MedicineRecordRepository extends JpaRepository<MedicineRecord, Integer> {
    @Query("SELECT DISTINCT m.date FROM MedicineRecord m WHERE m.appUserId.id = ?1")
    List<Date> findDistinctDate(Integer userId);
    @Query("SELECT m FROM MedicineRecord m WHERE m.date = ?1 AND m.appUserId.id = ?2")
    List<MedicineRecord> findByDate(Date date, Integer userId);
    @Query("SELECT m FROM MedicineRecord m WHERE m.appUserId.id = ?1")
    List<MedicineRecord> findByAppUser(Integer userId);
    @Query("SELECT m FROM MedicineRecord m WHERE m.appUserId.id = ?1 and m.weekStart = ?2")
    List<MedicineRecord> findByAppUserAndWeekStart(Integer userId,Date date);
    @Query("SELECT m FROM MedicineRecord m WHERE m.weekStart = ?1 AND m.appUserId.id = ?2 AND m.medicineType.id = ?3 ")
    List<MedicineRecord> findByWeekStartMedicineAppUser(Date date, Integer userId,Integer medicine);
    @Query("SELECT m FROM MedicineRecord m WHERE m.date = ?1 AND m.appUserId.id = ?2 AND m.medicineType.id = ?3 ")
    Optional<MedicineRecord> findByDateAndMedicine(Date date, Integer userId, Integer medicine);
}
