package vn.edu.fpt.SmartHealthC.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeQuestion;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String Question;
    @Enumerated(EnumType.STRING)
    private TypeQuestion Type;
    private int QuestionNumber;
}
