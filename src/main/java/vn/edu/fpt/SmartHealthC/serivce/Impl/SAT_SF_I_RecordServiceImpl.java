package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_I_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.SAT_SF_I_RecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_C_RecordService;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_I_RecordService;

import java.util.List;
import java.util.Optional;

@Service
public class SAT_SF_I_RecordServiceImpl implements SAT_SF_I_RecordService {

    @Autowired
    private SAT_SF_I_RecordRepository sat_sf_i_recordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public SAT_SF_I_Record createSAT_SF_I_Record(SAT_SF_I_RecordDTO sat_sf_i_recordDTO)
    {
        SAT_SF_I_Record sat_sf_i_record =  SAT_SF_I_Record.builder()
                .overallPoint(sat_sf_i_recordDTO.getOverallPoint())
                .monthStart(sat_sf_i_recordDTO.getMonthStart())
                .selfControl(sat_sf_i_recordDTO.getSelfControl())
                .stressFacing(sat_sf_i_recordDTO.getStressFacing())
                .consistency(sat_sf_i_recordDTO.getConsistency())
                .energyConservation(sat_sf_i_recordDTO.getEnergyConservation())
                .motivation(sat_sf_i_recordDTO.getMotivation())
                .revision(sat_sf_i_recordDTO.getRevision())
         .build();
        Optional<AppUser> appUser = appUserRepository.findById(sat_sf_i_recordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        sat_sf_i_record.setAppUserId(appUser.get());
        return  sat_sf_i_recordRepository.save(sat_sf_i_record);
    }

    @Override
    public SAT_SF_I_Record getSAT_SF_I_RecordById(Integer id) {
        Optional<SAT_SF_I_Record> sat_sf_i_record = sat_sf_i_recordRepository.findById(id);
        if(sat_sf_i_record.isEmpty()) {
            throw new AppException(ErrorCode.SAT_SF_I_NOT_FOUND);
        }
        return sat_sf_i_record.get();
    }

    @Override
    public List<SAT_SF_I_Record> getAllSAT_SF_I_Records() {
        return sat_sf_i_recordRepository.findAll();
    }

    @Override
    public SAT_SF_I_Record updateSAT_SF_I_Record(Integer id,SAT_SF_I_RecordDTO sat_sf_i_recordDTO) {
        SAT_SF_I_Record sat_sf_i_record =  getSAT_SF_I_RecordById(id);

        sat_sf_i_record.setOverallPoint(sat_sf_i_recordDTO.getOverallPoint());
        sat_sf_i_record.setMonthStart(sat_sf_i_recordDTO.getMonthStart());
        sat_sf_i_record.setSelfControl(sat_sf_i_recordDTO.getSelfControl());
        sat_sf_i_record.setStressFacing(sat_sf_i_recordDTO.getStressFacing());
        sat_sf_i_record.setConsistency(sat_sf_i_recordDTO.getConsistency());
        sat_sf_i_record.setEnergyConservation(sat_sf_i_recordDTO.getEnergyConservation());
        sat_sf_i_record.setMotivation(sat_sf_i_recordDTO.getMotivation());
        sat_sf_i_record.setRevision(sat_sf_i_recordDTO.getRevision());
        return  sat_sf_i_recordRepository.save(sat_sf_i_record);
    }

    @Override
    public SAT_SF_I_Record deleteSAT_SF_I_Record(Integer id) {
        SAT_SF_I_Record sat_sf_i_record = getSAT_SF_I_RecordById(id);
        sat_sf_i_recordRepository.deleteById(id);
        return sat_sf_i_record;
    }


}