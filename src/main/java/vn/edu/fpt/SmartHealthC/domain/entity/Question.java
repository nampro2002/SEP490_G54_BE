package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;

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
    private AppUser appUserId;

    @ManyToOne
    @JoinColumn(name = "webuser_id")
    private WebUser webUserId;

    @Enumerated(EnumType.STRING)
    private TypeUserQuestion typeUserQuestion;

    private String title;

    private String body;

    private String answer;

    private boolean gender;

    private Float height;

    private Float weight;

    private String phoneNumber;

}
