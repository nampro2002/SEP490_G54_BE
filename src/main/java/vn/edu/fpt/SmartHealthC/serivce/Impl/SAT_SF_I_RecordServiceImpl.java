package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.SAT_SF_C_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.SAT_SF_I_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;
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
        AppUser appUser = appUserRepository.findById(sat_sf_i_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sat_sf_i_record.setAppUserId(appUser);
        return  sat_sf_i_recordRepository.save(sat_sf_i_record);
    }

    @Override
    public Optional<SAT_SF_I_Record> getSAT_SF_I_RecordById(Integer id) {
        return sat_sf_i_recordRepository.findById(id);
    }

    @Override
    public List<SAT_SF_I_Record> getAllSAT_SF_I_Records() {
        return sat_sf_i_recordRepository.findAll();
    }

    @Override
    public SAT_SF_I_Record updateSAT_SF_I_Record(SAT_SF_I_RecordDTO sat_sf_i_recordDTO) {
        SAT_SF_I_Record sat_sf_i_record =  SAT_SF_I_Record.builder()
                .Id(sat_sf_i_recordDTO.getId())
                .overallPoint(sat_sf_i_recordDTO.getOverallPoint())
                .monthStart(sat_sf_i_recordDTO.getMonthStart())
                .selfControl(sat_sf_i_recordDTO.getSelfControl())
                .stressFacing(sat_sf_i_recordDTO.getStressFacing())
                .consistency(sat_sf_i_recordDTO.getConsistency())
                .energyConservation(sat_sf_i_recordDTO.getEnergyConservation())
                .motivation(sat_sf_i_recordDTO.getMotivation())
                .revision(sat_sf_i_recordDTO.getRevision())
                .build();
        AppUser appUser = appUserRepository.findById(sat_sf_i_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sat_sf_i_record.setAppUserId(appUser);
        return  sat_sf_i_recordRepository.save(sat_sf_i_record);
    }

    @Override
    public void deleteSAT_SF_I_Record(Integer id) {
        sat_sf_i_recordRepository.deleteById(id);
    }


}