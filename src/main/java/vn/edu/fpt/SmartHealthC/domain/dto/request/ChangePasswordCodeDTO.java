package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordCodeDTO {
    @NotBlank(message = "missing email")
    private String email;
//    @NotBlank(message = "missing code")
//    private String code;
    @NotBlank(message = "missing password")
    private String password;

}
