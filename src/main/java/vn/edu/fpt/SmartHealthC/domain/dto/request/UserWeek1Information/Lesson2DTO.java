package vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson2DTO {
//    @NotBlank(message = "missing strength")
    private String strength;
//    @NotBlank(message = "missing weakPoint")
    private String weakPoint;



}
