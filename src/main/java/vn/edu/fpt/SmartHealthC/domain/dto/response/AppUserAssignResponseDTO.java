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
public class AppUserAssignResponseDTO {
    private Integer accountId;

    private String email;

    private Integer appUserId;

    private String name;

    private String cic;

    private Date dob;

    private boolean gender;

    private String phoneNumber;
    private String msName;
}
