package vn.edu.fpt.SmartHealthC.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDetailResponseDTO {
    private Integer accountId;
    private String email;
    private Integer appUserId;
    private String name;
    private String cic;
    private Date dob;
    private boolean gender;
    private Float height;
    private Float weight;
    private String phoneNumber;
    private String msName;
    private String chronicDiseases;
    private String othersDiseases;
    private Boolean smoke;
    private Boolean alcohol;
    private Float bmi;
}
