package vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentalResponse {

    private Integer point;
    private Date date;



}
