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
public class Lesson3DTO {
    @NotBlank(message = "missing closePerson1")
    private String closePerson1;
    @NotBlank(message = "missing closePerson2")
    private String closePerson2;
    @NotBlank(message = "missing closePerson1Message")
    private String closePerson1Message;
    @NotBlank(message = "missing closePerson2Message")
    private String closePerson2Message;
    @NotBlank(message = "missing prefrerredEnvironment")
    private String prefrerredEnvironment;
    @NotNull(message = "missing prefrerredTime")
    private Date prefrerredTime;
    @NotBlank(message = "missing notPreferredLocation")
    private String notPreferredLocation;
    @NotBlank(message = "missing notPreferredTime")
    private Date notPreferredTime;




}