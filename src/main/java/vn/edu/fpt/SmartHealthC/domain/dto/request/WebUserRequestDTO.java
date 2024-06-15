package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebUserRequestDTO {
    @NotBlank(message = "missing email")
    private String email;
    @NotBlank(message = "missing password")
    private String password;
    @NotBlank(message = "missing username")
    private String username;
    @NotNull(message = "missing dob")
    private Date dob;
    @NotNull(message = "missing gender")
    private boolean gender;
    @NotBlank(message = "missing phoneNumber")
    private String phoneNumber;
    @NotNull(message = "missing typeAccount")
    private TypeAccount typeAccount;

}
