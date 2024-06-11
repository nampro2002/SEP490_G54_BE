package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.SF_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.SF_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SF_Record;

import java.util.List;
import java.util.Optional;

public interface SF_RecordService {
    SF_Record createSF_Record(SF_RecordDTO sf_recordDTO);
    SF_Record getSF_RecordById(Integer id);
    List<SF_Record> getAllSF_Records();
    SF_Record updateSF_Record(Integer id, SF_RecordDTO sf_recordDTO);
    SF_Record deleteSF_Record(Integer id);
}