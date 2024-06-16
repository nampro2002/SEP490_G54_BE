package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be minimum 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;

    private String name;

    private String cic;

    private Date dob;

    private boolean gender;

    private Float height;

    private Float weight;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(
            regexp = "^((010-\\d{4}-\\d{4})|(0\\d{1,2}-\\d{3,4}-\\d{4}))$",
            message = "Invalid Korean phone number"
    )
    private String phoneNumber;
    private List<Integer> listMedicalHistory;
}
