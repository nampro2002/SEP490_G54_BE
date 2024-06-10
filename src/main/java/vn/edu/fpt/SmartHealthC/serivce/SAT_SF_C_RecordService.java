package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_C_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;

import java.util.List;
import java.util.Optional;

public interface SAT_SF_C_RecordService {
    SAT_SF_C_Record createSAT_SF_C_Record(SAT_SF_C_RecordDTO sat_sf_c_recordDTO);
   SAT_SF_C_Record getSAT_SF_C_RecordById(Integer id);
    List<SAT_SF_C_Record> getAllSAT_SF_C_Records();
    SAT_SF_C_Record updateSAT_SF_C_Record(Integer id,SAT_SF_C_RecordDTO sat_sf_c_recordDTO);
    SAT_SF_C_Record deleteSAT_SF_C_Record(Integer id);
}