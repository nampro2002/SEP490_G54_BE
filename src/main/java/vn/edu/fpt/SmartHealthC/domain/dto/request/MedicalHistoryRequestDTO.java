package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryRequestDTO {

    private String name;

    private TypeMedicalHistory type;

    private boolean isDeleted;
}
