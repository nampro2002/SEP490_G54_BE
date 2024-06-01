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
public class UserLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser AppUserId;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson LessonId;

    private Date LessonDate;


}
