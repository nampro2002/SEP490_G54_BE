package vn.edu.fpt.SmartHealthC.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQRequestDTO {
    private Integer id;

    private String question;

    private String answer;

}
