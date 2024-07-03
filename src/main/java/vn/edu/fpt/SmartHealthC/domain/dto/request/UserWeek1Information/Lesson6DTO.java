package vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson6DTO {
    @NotNull(message = "missing noMoreThan2")
    private Boolean noMoreThan2;
    @NotNull(message = "missing todoList")
    private Boolean todoList;
    @NotNull(message = "missing noProcastinating")
    private Boolean noProcastinating;
    @NotNull(message = "missing doExercises")
    private Boolean doExercises;


}
