package vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardinalTypeTimeMeasureDTO {
    private Boolean beforeBreakfast = false;
    private Boolean afterBreakfast= false;
    private Boolean beforeLunch= false;
    private Boolean afterLunch= false;
    private Boolean beforeDinner= false;
    private Boolean afterDinner= false;

}
