package vn.edu.fpt.SmartHealthC.domain.dto.request;


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
    private String email;

    private String password;

    private String username;

    private Date dob;
    private boolean gender;

    private String phoneNumber;
    private TypeAccount typeAccount;

}
