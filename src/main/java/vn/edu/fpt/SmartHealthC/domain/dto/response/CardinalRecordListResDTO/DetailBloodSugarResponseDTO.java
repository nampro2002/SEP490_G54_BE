package vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailBloodSugarResponseDTO {

    private Float data;
    private String typeTimeMeasure;
}
