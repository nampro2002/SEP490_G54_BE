package vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HBA1CResponseDTO {

    private Float data;
    private Date date;
}