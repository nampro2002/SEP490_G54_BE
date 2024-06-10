package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_I_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;

import java.util.List;
import java.util.Optional;

public interface SAT_SF_I_RecordService {
    SAT_SF_I_Record createSAT_SF_I_Record(SAT_SF_I_RecordDTO sat_sf_i_recordDTO);
    SAT_SF_I_Record getSAT_SF_I_RecordById(Integer id);
    List<SAT_SF_I_Record> getAllSAT_SF_I_Records();
    SAT_SF_I_Record updateSAT_SF_I_Record(Integer id,SAT_SF_I_RecordDTO sat_sf_i_recordDTO);
    SAT_SF_I_Record deleteSAT_SF_I_Record(Integer id);
}