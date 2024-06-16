package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgetPasswordCodeDTO {
    @NotBlank(message = "missing email")
    private String email;
    @NotBlank(message = "missing code")
    private String code;
    @NotBlank(message = "missing password")
    private String password;

}
