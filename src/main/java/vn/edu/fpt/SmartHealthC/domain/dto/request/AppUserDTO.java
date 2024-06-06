package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDTO {

    private int id;

    private int accountId;

    private int webUserId;

    private String name;

    private String hospitalNumber;

    private Date dob;

    private boolean gender;

    private Float height;

    private Float weight;

    private String  phoneNumber;
    private String strength;
    private String weakness;

}
