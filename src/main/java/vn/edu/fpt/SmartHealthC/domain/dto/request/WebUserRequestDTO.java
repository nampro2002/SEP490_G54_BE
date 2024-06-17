package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be minimum 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;
    @NotBlank(message = "missing username")
    private String username;
    @NotNull(message = "missing dob")
    private Date dob;
    @NotNull(message = "missing gender")
    private Boolean gender;
    @NotBlank(message = "missing phoneNumber")
    private String phoneNumber;
    @NotNull(message = "missing typeAccount")
    private TypeAccount typeAccount;

}
