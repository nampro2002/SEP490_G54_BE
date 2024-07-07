package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebUserResponseDTO {
    private Integer accountId;

    private String email;

    private Integer webUserId;

    private String name;

    private Date dob;

    private boolean gender;

    private String  phoneNumber;

}
