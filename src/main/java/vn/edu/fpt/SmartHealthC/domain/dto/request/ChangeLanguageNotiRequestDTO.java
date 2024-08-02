package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeLanguageNotiRequestDTO {
    @NotBlank(message = "Device token is required")
    private String deviceToken;
    @NotBlank(message = "Language is required")
    private TypeLanguage language;
}
