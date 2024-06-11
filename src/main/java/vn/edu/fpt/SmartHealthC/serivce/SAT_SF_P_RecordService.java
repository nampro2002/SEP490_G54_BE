package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_P_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;

import java.util.List;
import java.util.Optional;

public interface SAT_SF_P_RecordService {
    SAT_SF_P_Record createSAT_SF_P_Record(SAT_SF_P_RecordDTO sat_sf_p_recordDTO);
    SAT_SF_P_Record getSAT_SF_P_RecordById(Integer id);
    List<SAT_SF_P_Record> getAllSAT_SF_P_Records();
    SAT_SF_P_Record updateSAT_SF_P_Record(Integer id, SAT_SF_P_RecordDTO sat_sf_p_recordDTO);
    SAT_SF_P_Record deleteSAT_SF_P_Record(Integer id);
}