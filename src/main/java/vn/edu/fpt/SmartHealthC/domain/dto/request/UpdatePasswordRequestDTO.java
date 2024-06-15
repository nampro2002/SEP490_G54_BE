package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePasswordRequestDTO {
    @NotBlank(message = "missing oldPassword")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be minimum 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String oldPassword;
    @NotBlank(message = "missing newPassword")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be minimum 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String newPassword;
}
