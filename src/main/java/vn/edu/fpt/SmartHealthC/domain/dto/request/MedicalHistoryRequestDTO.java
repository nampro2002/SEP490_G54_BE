package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "missing name")
    private String name;
    @NotNull(message = "missing typeMedicalHistory")
    private TypeMedicalHistory type;

    private boolean isDeleted = false;
}
