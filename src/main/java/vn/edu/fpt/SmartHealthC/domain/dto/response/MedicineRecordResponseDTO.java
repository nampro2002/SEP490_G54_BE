package vn.edu.fpt.SmartHealthC.domain.dto.response;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineRecordResponseDTO {
    private Integer id;

    private String appUserName;

    private Date weekStart;

    private String medicineType;

    private Float hour;

    private Date date;

    private Boolean status;
}
