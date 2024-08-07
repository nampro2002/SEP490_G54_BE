package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebUserUpdateRequestDTO {
    @NotBlank(message = "missing username")
    private String name;
    @NotNull(message = "missing dob")
    private Date dob;
    @NotNull(message = "missing gender")
    private Boolean gender;
    @Pattern(
            regexp = "^0\\d{8,10}$",
            message = "Phone number must start with 0 and has 8-10 digits following"
    )
    @NotBlank(message = "missing phoneNumber")
    private String phoneNumber;
}
