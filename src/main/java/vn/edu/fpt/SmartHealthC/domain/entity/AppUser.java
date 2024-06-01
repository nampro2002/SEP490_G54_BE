package vn.edu.fpt.SmartHealthC.domain.entity;


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
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account AccountId;

    @OneToOne
    @JoinColumn(name = "webuser_id")
    private WebUser WebUserId;

    private String Name;

    private String HospitalNumber;

    private Date Dob;

    private boolean Gender;

    private Float Height;

    private Float Weight;

    private String PhoneNumber;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Question> Question;


    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ActivityRecord> ActivityRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<BloodPressureRecord> BloodPressureRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<DietRecord> DietRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<MedicalAppointment> MedicalAppointments;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<MedicineRecord> MedicineRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<MentalRecord> MentalRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<MonthlyQuestion> MonthlyQuestions;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SAT_SF_C_Record> SAT_SF_C_Records;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SAT_SF_I_Record> SAT_SF_I_Records;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SAT_SF_P_Record> SAT_SF_P_Records;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SF_Record> SF_Records;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<StepRecord> StepRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<WeightRecord> WeightRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<UserChronicDisease> UserChronicDiseases;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<UserMedicalRecord> UserMedicalRecords;

    @OneToMany(mappedBy = "AppUserId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<UserLesson> UserLessons;



}
