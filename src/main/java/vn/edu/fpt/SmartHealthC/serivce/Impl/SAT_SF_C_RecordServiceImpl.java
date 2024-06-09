package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_C_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_C_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
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
        AppUser appUser = appUserRepository.findById(sat_sf_c_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sat_sf_c_record.setAppUserId(appUser);
        return  sat_sf_c_recordRepository.save(sat_sf_c_record);
    }

    @Override
    public Optional<SAT_SF_C_Record> getSAT_SF_C_RecordById(Integer id) {
        return sat_sf_c_recordRepository.findById(id);
    }

    @Override
    public List<SAT_SF_C_Record> getAllSAT_SF_C_Records() {
        return sat_sf_c_recordRepository.findAll();
    }

    @Override
    public SAT_SF_C_Record updateSAT_SF_C_Record(SAT_SF_C_RecordDTO sat_sf_c_recordDTO) {
        SAT_SF_C_Record sat_sf_c_record =  SAT_SF_C_Record.builder()
                .id(sat_sf_c_recordDTO.getId())
                .overallPoint(sat_sf_c_recordDTO.getOverallPoint())
                .monthStart(sat_sf_c_recordDTO.getMonthStart())
                .independence(sat_sf_c_recordDTO.getIndependence())
                .optimistic(sat_sf_c_recordDTO.getOptimistic())
                .relationship(sat_sf_c_recordDTO.getRelationship())
                .sharedStory(sat_sf_c_recordDTO.getSharedStory())
                .build();
        AppUser appUser = appUserRepository.findById(sat_sf_c_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sat_sf_c_record.setAppUserId(appUser);
        return  sat_sf_c_recordRepository.save(sat_sf_c_record);
    }

    @Override
    public void deleteSAT_SF_C_Record(Integer id) {
        sat_sf_c_recordRepository.deleteById(id);
    }


}