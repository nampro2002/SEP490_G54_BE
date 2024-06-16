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
public class FAQRequestDTO {
    @NotBlank(message = "missing question")
    private String question;
    @NotBlank(message = "missing answer")
    private String answer;

}
