package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonRequestDTO {
    private String title;

    private String video;

    private String content;

    private Integer lessonNumber;

}
