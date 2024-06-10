package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_C_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.SAT_SF_C_RecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_C_RecordService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class SAT_SF_C_RecordServiceImpl implements SAT_SF_C_RecordService {

    @Autowired
    private SAT_SF_C_RecordRepository sat_sf_c_recordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public SAT_SF_C_Record createSAT_SF_C_Record(SAT_SF_C_RecordDTO sat_sf_c_recordDTO)
    {
        SAT_SF_C_Record sat_sf_c_record =  SAT_SF_C_Record.builder()
                .overallPoint(sat_sf_c_recordDTO.getOverallPoint())
                .monthStart(sat_sf_c_recordDTO.getMonthStart())
                .independence(sat_sf_c_recordDTO.getIndependence())
                .optimistic(sat_sf_c_recordDTO.getOptimistic())
                .relationship(sat_sf_c_recordDTO.getRelationship())
                .sharedStory(sat_sf_c_recordDTO.getSharedStory())
        .build();
        Optional<AppUser> appUser = appUserRepository.findById(sat_sf_c_recordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        sat_sf_c_record.setAppUserId(appUser.get());
        return  sat_sf_c_recordRepository.save(sat_sf_c_record);
    }

    @Override
    public SAT_SF_C_Record getSAT_SF_C_RecordById(Integer id) {
        Optional<SAT_SF_C_Record> sat_sf_c_record = sat_sf_c_recordRepository.findById(id);
        if(sat_sf_c_record.isEmpty()) {
            throw new AppException(ErrorCode.SAT_SF_C_NOT_FOUND);
        }
        return sat_sf_c_record.get();
    }

    @Override
    public List<SAT_SF_C_Record> getAllSAT_SF_C_Records() {
        return sat_sf_c_recordRepository.findAll();
    }

    @Override
    public SAT_SF_C_Record updateSAT_SF_C_Record(Integer id,SAT_SF_C_RecordDTO sat_sf_c_recordDTO) {

        SAT_SF_C_Record sat_sf_c_record = getSAT_SF_C_RecordById(id);
        sat_sf_c_record.setOverallPoint(sat_sf_c_recordDTO.getOverallPoint());
        sat_sf_c_record.setMonthStart(sat_sf_c_recordDTO.getMonthStart());
        sat_sf_c_record.setIndependence(sat_sf_c_recordDTO.getIndependence());
        sat_sf_c_record.setOptimistic(sat_sf_c_recordDTO.getOptimistic());
        sat_sf_c_record.setRelationship(sat_sf_c_recordDTO.getRelationship());
        sat_sf_c_record.setSharedStory(sat_sf_c_recordDTO.getSharedStory());
        return  sat_sf_c_recordRepository.save(sat_sf_c_record);
    }

    @Override
    public SAT_SF_C_Record deleteSAT_SF_C_Record(Integer id) {
        SAT_SF_C_Record sat_sf_c_record = getSAT_SF_C_RecordById(id);
        sat_sf_c_recordRepository.deleteById(id);
        return sat_sf_c_record;
    }


}