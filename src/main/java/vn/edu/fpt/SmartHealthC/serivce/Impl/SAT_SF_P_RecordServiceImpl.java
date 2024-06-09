package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.SAT_SF_P_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_I_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.SAT_SF_P_RecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_I_RecordService;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_P_RecordService;

import java.util.List;
import java.util.Optional;

@Service
public class SAT_SF_P_RecordServiceImpl implements SAT_SF_P_RecordService {

    @Autowired
    private SAT_SF_P_RecordRepository sat_sf_p_recordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public SAT_SF_P_Record createSAT_SF_P_Record(SAT_SF_P_RecordDTO sat_sf_p_recordDTO)
    {
        SAT_SF_P_Record sat_sf_p_record =  SAT_SF_P_Record.builder()
                .overallPoint(sat_sf_p_recordDTO.getOverallPoint())
                .monthStart(sat_sf_p_recordDTO.getMonthStart())
                .lifePursuit(sat_sf_p_recordDTO.getLifePursuit())
                .planning(sat_sf_p_recordDTO.getPlanning())
                .rightDecision(sat_sf_p_recordDTO.getRightDecision())
                .priorityFocus(sat_sf_p_recordDTO.getPriorityFocus())
                .healthyEnvironment(sat_sf_p_recordDTO.getHealthyEnvironment())
         .build();
        AppUser appUser = appUserRepository.findById(sat_sf_p_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sat_sf_p_record.setAppUserId(appUser);
        return  sat_sf_p_recordRepository.save(sat_sf_p_record);
    }

    @Override
    public Optional<SAT_SF_P_Record> getSAT_SF_P_RecordById(Integer id) {
        return sat_sf_p_recordRepository.findById(id);
    }

    @Override
    public List<SAT_SF_P_Record> getAllSAT_SF_P_Records() {
        return sat_sf_p_recordRepository.findAll();
    }

    @Override
    public SAT_SF_P_Record updateSAT_SF_P_Record(SAT_SF_P_RecordDTO sat_sf_p_recordDTO) {
        SAT_SF_P_Record sat_sf_p_record =  SAT_SF_P_Record.builder()
                .id(sat_sf_p_recordDTO.getId())
                .overallPoint(sat_sf_p_recordDTO.getOverallPoint())
                .monthStart(sat_sf_p_recordDTO.getMonthStart())
                .lifePursuit(sat_sf_p_recordDTO.getLifePursuit())
                .planning(sat_sf_p_recordDTO.getPlanning())
                .rightDecision(sat_sf_p_recordDTO.getRightDecision())
                .priorityFocus(sat_sf_p_recordDTO.getPriorityFocus())
                .healthyEnvironment(sat_sf_p_recordDTO.getHealthyEnvironment())
                .build();
        AppUser appUser = appUserRepository.findById(sat_sf_p_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sat_sf_p_record.setAppUserId(appUser);
        return  sat_sf_p_recordRepository.save(sat_sf_p_record);
    }

    @Override
    public void deleteSAT_SF_P_Record(Integer id) {
        sat_sf_p_recordRepository.deleteById(id);
    }


}