package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLessonDTO {

    private int id;
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing lessonId")
    private int  lessonId;
    @NotNull(message = "missing lessonDate")
    private Date lessonDate;


}
