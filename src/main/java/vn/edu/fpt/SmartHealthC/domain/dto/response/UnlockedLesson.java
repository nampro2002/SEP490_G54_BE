package vn.edu.fpt.SmartHealthC.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnlockedLesson {
    private Integer lesson;
    private Boolean statusCheck = false;
}
