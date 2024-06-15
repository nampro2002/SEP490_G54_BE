package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalRuleRequestDTO {
    private Integer id;
    @NotBlank(message = "missing title")
    private String title;
    private String description;
    @NotNull(message = "missing isDeleted")
    private boolean isDeleted;
}
