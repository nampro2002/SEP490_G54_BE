package vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeCardinalIndex;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordOfTime {
    private Float value;
    private TypeCardinalIndex typeCardinalIndex;
}