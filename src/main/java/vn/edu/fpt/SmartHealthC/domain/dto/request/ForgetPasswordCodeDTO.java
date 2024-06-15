package vn.edu.fpt.SmartHealthC.domain.dto.request;


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
    @NotNull(message = "missing email")
    private String email;
    @NotNull(message = "missing code")
    private String code;
    @NotNull(message = "missing expiredDate")
    private Date expiredDate;

}
