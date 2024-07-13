package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class WebUserUpdateRequestDTO {
    @NotBlank(message = "missing username")
    private String name;
    @NotNull(message = "missing dob")
    private Date dob;
    @NotNull(message = "missing gender")
    private Boolean gender;
    @NotBlank(message = "missing phoneNumber")
    private String phoneNumber;
}
