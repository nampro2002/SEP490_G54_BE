package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;



    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser AppUserId;

    @ManyToOne
    @JoinColumn(name = "webuser_id")
    private WebUser WebUserId;

    private String Title;

    private String Body;

    private String Answer;

    private boolean Gender;

    private Float Height;

    private Float Weight;

    private String PhoneNumber;

}
