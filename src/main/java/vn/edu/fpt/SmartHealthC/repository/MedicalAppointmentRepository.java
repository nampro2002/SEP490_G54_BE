package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;

import java.util.List;

public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Integer> {
    @Query("SELECT m FROM MedicalAppointment m WHERE LOWER(m.hospital) LIKE %?1%")
    Page<MedicalAppointment> findAll(Pageable pageable, String search);

    @Query("SELECT m FROM MedicalAppointment m WHERE m.statusMedicalAppointment = ?1 AND m.appUserId.webUser.id = ?2 AND m.typeMedicalAppointment = ?3")
    Page<MedicalAppointment> findAllPendingByUserIdAndType(TypeMedicalAppointmentStatus typeMedicalAppointmentStatus, Integer id, TypeMedicalAppointment type, Pageable paging);

    // m.typeMedicalAppointment != ?3
    @Query("SELECT m FROM MedicalAppointment m WHERE m.statusMedicalAppointment <> ?1 AND m.appUserId.id = ?2")
    List<MedicalAppointment> findAllByUserIdAndType(TypeMedicalAppointmentStatus type, Integer userId);

    @Query("SELECT m FROM MedicalAppointment m WHERE m.appUserId.webUser.id = ?1 AND LOWER(m.appUserId.name) LIKE %?2% and m.statusMedicalAppointment != 'DONE' order by m.date")
    Page<MedicalAppointment> findAllByWebUserId(Integer id, String search, Pageable paging);

    @Query("SELECT m FROM MedicalAppointment m WHERE m.appUserId.id = ?1 AND m.statusMedicalAppointment = ?2")
    Page<MedicalAppointment> findAllByAppUserId(Integer userId, TypeMedicalAppointmentStatus type, Pageable paging);

    @Query("SELECT m FROM MedicalAppointment m WHERE m.statusMedicalAppointment = ?1")
    List<MedicalAppointment> findAllByType(TypeMedicalAppointmentStatus typeMedicalAppointmentStatus);

//    @Query("SELECT m FROM MedicalAppointment m WHERE m.statusMedicalAppointment = ?1 AND m.appUserId.id = ?2")
//    Page<MedicalAppointment> findAllPendingByUserId(TypeMedicalAppointmentStatus type, Integer id, Pageable paging);

}
