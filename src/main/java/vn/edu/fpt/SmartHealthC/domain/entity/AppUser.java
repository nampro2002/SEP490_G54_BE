package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account accountId;


    @ManyToOne
    @JoinColumn(name = "web_user_id")
    @ToString.Exclude
    private WebUser webUser;

    private String name;
    //citizen identification card
    private String cic;

    private Date dob;

    private boolean gender;

    private Float height;

    private Float weight;

    private String phoneNumber;
    private String medicalSpecialistNote;

//    @OneToMany(mappedBy = "appUserId")
//    private List<Question> question;
//
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<ActivityRecord> activityRecords;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<BloodPressureRecord> bloodPressureRecords;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<DietRecord> dietRecords;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<MedicalAppointment> medicalAppointments;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<MedicineRecord> medicineRecords;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<MentalRecord> mentalRecords;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<MonthlyQuestion> monthlyQuestions;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<SAT_SF_C_Record> sat_sf_c_Records;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<SAT_SF_I_Record> sat_sf_i_Records;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<SAT_SF_P_Record> sat_sf_p_Records;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<SF_Record> sf_Records;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<StepRecord> stepRecords;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<WeightRecord> weightRecords;
//
    @OneToMany(mappedBy = "appUserId")
    private List<UserMedicalHistory> userMedicalHistoryList;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<UserLesson> userLessons;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<Cholesterol> cholesterols;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<BloodSugar> bloodSugars;
//
//    @OneToMany(mappedBy = "appUserId")
//    private List<HBA1CRecord> hba1cRecords;
}

