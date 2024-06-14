package vn.edu.fpt.SmartHealthC.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryResDTO {
    private Integer id;
    private String name;
    private TypeMedicalHistory type;
    private boolean isDeleted ;
}
