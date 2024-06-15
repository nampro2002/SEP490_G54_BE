package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "missing title")
    private String title;
    @NotNull(message = "missing video")
    private String video;
    @NotNull(message = "missing content")
    private String content;
    @NotNull(message = "missing lessonNumber")
    private Integer lessonNumber;

}
