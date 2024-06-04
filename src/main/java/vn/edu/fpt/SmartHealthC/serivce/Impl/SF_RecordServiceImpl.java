package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.SAT_SF_P_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.SF_RecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.SAT_SF_P_Record;
import vn.edu.fpt.SmartHealthC.domain.entity.SF_Record;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.SF_RecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.SAT_SF_P_RecordService;
import vn.edu.fpt.SmartHealthC.serivce.SF_RecordService;

import java.util.List;
import java.util.Optional;

@Service
public class SF_RecordServiceImpl implements SF_RecordService {

    @Autowired
    private SF_RecordRepository sf_recordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public SF_Record createSF_Record(SF_RecordDTO sf_recordDTO)
    {
        SF_Record sf_record =  SF_Record.builder()
                .monthStart(sf_recordDTO.getMonthStart())
                .positivity(sf_recordDTO.getPositivity())
                .activity(sf_recordDTO.getActivity())
                .diet(sf_recordDTO.getDiet())
                .medication(sf_recordDTO.getMedication())
                .activityPlanning(sf_recordDTO.getActivityPlanning())
                .activityHabit(sf_recordDTO.getActivityHabit())
                .healthyDiet(sf_recordDTO.getHealthyDiet())
                .vegetablePrioritization(sf_recordDTO.getVegetablePrioritization())
                .dietHabit(sf_recordDTO.getDietHabit())
                .planCompliance(sf_recordDTO.getPlanCompliance())
                .medicationHabit(sf_recordDTO.getMedicationHabit())
         .build();
        AppUser appUser = appUserRepository.findById(sf_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sf_record.setAppUserId(appUser);
        return  sf_recordRepository.save(sf_record);
    }

    @Override
    public Optional<SF_Record> getSF_RecordById(Integer id) {
        return sf_recordRepository.findById(id);
    }

    @Override
    public List<SF_Record> getAllSF_Records() {
        return sf_recordRepository.findAll();
    }

    @Override
    public SF_Record updateSF_Record(SF_RecordDTO sf_recordDTO) {
        SF_Record sf_record =  SF_Record.builder()
                .Id(sf_recordDTO.getId())
                .monthStart(sf_recordDTO.getMonthStart())
                .positivity(sf_recordDTO.getPositivity())
                .activity(sf_recordDTO.getActivity())
                .diet(sf_recordDTO.getDiet())
                .medication(sf_recordDTO.getMedication())
                .activityPlanning(sf_recordDTO.getActivityPlanning())
                .activityHabit(sf_recordDTO.getActivityHabit())
                .healthyDiet(sf_recordDTO.getHealthyDiet())
                .vegetablePrioritization(sf_recordDTO.getVegetablePrioritization())
                .dietHabit(sf_recordDTO.getDietHabit())
                .planCompliance(sf_recordDTO.getPlanCompliance())
                .medicationHabit(sf_recordDTO.getMedicationHabit())
                .build();
        AppUser appUser = appUserRepository.findById(sf_recordDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        sf_record.setAppUserId(appUser);
        return  sf_recordRepository.save(sf_record);
    }

    @Override
    public void deleteSF_Record(Integer id) {
        sf_recordRepository.deleteById(id);
    }


}