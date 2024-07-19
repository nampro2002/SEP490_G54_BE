package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$",
            message = "Password must be minimum 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;
    @NotBlank(message = "missing name")
    private String name;
    @NotBlank(message = "missing cic")
    private String cic;
    @NotNull(message = "missing dob")
    private Date dob;
    @NotNull(message = "missing gender")
    private Boolean gender;
    @NotNull(message = "missing height")
    private Float height;
    @NotNull(message = "missing weight")
    private Float weight;
    @NotBlank(message = "missing phone number")
    @Pattern(
            regexp = "^0\\d{8,10}$",
            message = "Phone number must start with 0 and has 8-10 digits following"
    )
    private String phoneNumber;
    private List<Integer> listMedicalHistory;
}
