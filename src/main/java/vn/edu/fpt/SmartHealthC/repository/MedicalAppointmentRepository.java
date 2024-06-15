package vn.edu.fpt.SmartHealthC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;

public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Integer> {
    @Query("SELECT m FROM MedicalAppointment m WHERE m.hospital LIKE %?1%")
    Page<MedicalAppointment> findAll(Pageable pageable, String search);

    @Query("SELECT m FROM MedicalAppointment m WHERE m.statusMedicalAppointment = ?1 AND m.appUserId.webUser.accountId.Id = ?2 AND m.typeMedicalAppointment = ?3")
    Page<MedicalAppointment> findAllPendingByUserIdAndType(TypeMedicalAppointmentStatus typeMedicalAppointmentStatus, Integer id, TypeMedicalAppointment type, Pageable paging);

//    @Query("SELECT m FROM MedicalAppointment m WHERE m.statusMedicalAppointment = ?1 AND m.appUserId.id = ?2")
//    Page<MedicalAppointment> findAllPendingByUserId(TypeMedicalAppointmentStatus type, Integer id, Pageable paging);

}
