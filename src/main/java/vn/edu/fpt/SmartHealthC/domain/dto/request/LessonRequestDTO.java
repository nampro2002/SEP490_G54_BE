package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "missing title")
    private String title;
    @NotBlank(message = "missing video")
    private String video;
    @NotBlank(message = "missing content")
    private String content;
    @NotNull(message = "missing lessonNumber")
    private Integer lessonNumber;

}
