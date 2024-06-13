package vn.edu.fpt.SmartHealthC.domain.dto.response;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponseDTO {
    private Integer accountId;

    private String email;

    private Integer appUserId;

    private String name;

    private String cic;

    private Date dob;

    private boolean gender;

    private String  phoneNumber;

}
