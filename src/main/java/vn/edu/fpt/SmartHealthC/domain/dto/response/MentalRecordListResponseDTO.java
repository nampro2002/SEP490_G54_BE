package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalRecordListResponseDTO {
    private Integer id;
    private Float point;
    private String weekStart;
    private String date;
    private String mentalRule;
}
