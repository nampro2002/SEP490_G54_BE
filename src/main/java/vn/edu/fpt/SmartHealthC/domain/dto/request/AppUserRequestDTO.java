package vn.edu.fpt.SmartHealthC.domain.dto.request;


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

    private String name;

    private String cic;

    private Date dob;

    private boolean gender;

    private Float height;

    private Float weight;

    private String  phoneNumber;

}
