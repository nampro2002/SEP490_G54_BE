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
public class AppUserRequestDTO {
    @NotBlank(message = "missing name")
    private String name;
    @NotBlank(message = "missing cic")
    private String cic;
    @NotNull(message = "missing dob")
    private Date dob;
    @NotNull(message = "missing gender")
    private boolean gender;
    @NotNull(message = "missing height")
    private Float height;
    @NotNull(message = "missing weight")
    private Float weight;
    @NotBlank(message = "missing phoneNumber")
    private String  phoneNumber;

}
