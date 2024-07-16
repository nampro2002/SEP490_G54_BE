package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.*;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MonthlyRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MonthlyQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.MonthlyQuestionService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
  public class MonthlyQuestionServiceImpl implements MonthlyQuestionService {

    @Autowired
    private MonthlyQuestionRepository monthlyQuestionRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;


    @Override
    public void createNewMonthMark(int appUserId) {
        Optional<AppUser> appUser = appUserRepository.findById(appUserId);
        Integer lastMonth = monthlyQuestionRepository.findByAppUser(appUser.get().getId());
        MonthlyRecord monthlyRecord = MonthlyRecord.builder()
                .appUserId(appUser.get())
                .monthNumber(lastMonth == null ? 0 : lastMonth+1)
                .monthlyRecordType(MonthlyRecordType.NEW_MONTH_MARK)
                .build();
        monthlyQuestionRepository.save(monthlyRecord);
    }

    @Override
    public void createAnswers(List<MonthlyQuestionDTO> monthlyQuestionDTO) {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        for (MonthlyQuestionDTO record : monthlyQuestionDTO) {
            MonthlyRecord monthlyRecord = MonthlyRecord.builder()
                    .appUserId(appUser)
                    .monthNumber(record.getMonthNumber())
                    .monthlyRecordType(record.getMonthlyRecordType())
                    .questionNumber(record.getQuestionNumber())
                    .question(record.getQuestion())
                    .answer(record.getAnswer())
                    .build();
            monthlyQuestionRepository.save(monthlyRecord);
        }
    }

    @Override
    public List<MonthlyNumberResponseDTO> getList3MonthlyNumber() {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        List<MonthlyNumberResponseDTO> monthlyNumberResponseDTOList = new ArrayList<>();
        List<Integer> monthlyNumbers = monthlyQuestionRepository.find3ByAppUser(appUser.getId());
        for (Integer monthNumber : monthlyNumbers) {
            boolean isAnswered= monthlyQuestionRepository.countMonthNumberByAppUser(appUser.getId(),monthNumber) > 1;
          MonthlyNumberResponseDTO monthlyNumberResponseDTO = new MonthlyNumberResponseDTO().builder()
                  .monthNumber(monthNumber)
                  .isAnswered(isAnswered)
                  .build();
            monthlyNumberResponseDTOList.add(monthlyNumberResponseDTO);
        }
        return monthlyNumberResponseDTOList;
    }

    @Override
    public List<MonthlyAnswerResponseDTO> getWebListAnswer(int userId, int monthNumber,String type) {
        return getAnswerByMonthNumberAndAppUser(monthNumber , type , userId);
    }
    @Override
    public List<MonthlyAnswerResponseDTO> getMobileListAnswer(int monthNumber, String type) {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        return getAnswerByMonthNumberAndAppUser(monthNumber , type , appUser.getId());
    }
    public List<MonthlyAnswerResponseDTO> getAnswerByMonthNumberAndAppUser(int monthNumber, String type , int appUserId) {
        List<MonthlyAnswerResponseDTO> monthlyAnswerResponseDTOList = new ArrayList<>();
        List<MonthlyRecord> monthlyNumbers = monthlyQuestionRepository.findAllByAppUserAndMonthNumber(appUserId,monthNumber);
        MonthlyRecordType monthlyRecordType;
        try {
            monthlyRecordType = MonthlyRecordType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.MONTHLY_TYPE_NOT_FOUND);
        }
        for (MonthlyRecord record : monthlyNumbers) {
            if(record.getMonthlyRecordType().equals(monthlyRecordType)){
                MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                        .questionNumber(record.getQuestionNumber())
                        .question(record.getQuestion())
                        .type(record.getMonthlyRecordType())
                        .answer(record.getAnswer())
                        .build();
                monthlyAnswerResponseDTOList.add(monthlyNumberResponseDTO);
            }
        }
        return monthlyAnswerResponseDTOList;
    }



    @Override
    public MonthlyStatisticResponseDTO getPoint(Integer monthNumber, Integer appUser) {
//        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);

        SatResponseDTO satResponseDTO = new SatResponseDTO();
        SFResponseDTO sfResponseDTO = new SFResponseDTO();


        List<MonthlyAnswerResponseDTO> sat_sf_c_List = new ArrayList<>();
        List<MonthlyAnswerResponseDTO> sat_sf_p_List = new ArrayList<>();
        List<MonthlyAnswerResponseDTO> sat_sf_i_List = new ArrayList<>();
        List<MonthlyAnswerResponseDTO> mentalList = new ArrayList<>();
        List<MonthlyAnswerResponseDTO> activityList = new ArrayList<>();
        List<MonthlyAnswerResponseDTO> dietList = new ArrayList<>();
        List<MonthlyAnswerResponseDTO> medicineList = new ArrayList<>();
        List<MonthlyRecord> monthlyNumbers = monthlyQuestionRepository.findAllByAppUserAndMonthNumber(appUser,monthNumber);
        if(!monthlyNumbers.isEmpty()){
            for (MonthlyRecord record : monthlyNumbers) {
                if(record.getMonthlyRecordType() == MonthlyRecordType.SAT_SF_C){
                    MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                            .questionNumber(record.getQuestionNumber())
                            .question(record.getQuestion())
                            .type(record.getMonthlyRecordType())
                            .answer(record.getAnswer())
                            .build();
                    sat_sf_c_List.add(monthlyNumberResponseDTO);
                }
                if(record.getMonthlyRecordType() == MonthlyRecordType.SAT_SF_P){
                    MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                            .questionNumber(record.getQuestionNumber())
                            .question(record.getQuestion())
                            .type(record.getMonthlyRecordType())
                            .answer(record.getAnswer())
                            .build();
                    sat_sf_p_List.add(monthlyNumberResponseDTO);
                }
                if(record.getMonthlyRecordType() == MonthlyRecordType.SAT_SF_I){
                    MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                            .questionNumber(record.getQuestionNumber())
                            .question(record.getQuestion())
                            .type(record.getMonthlyRecordType())
                            .answer(record.getAnswer())
                            .build();
                    sat_sf_i_List.add(monthlyNumberResponseDTO);
                }
                if(record.getMonthlyRecordType() == MonthlyRecordType.SF_MENTAL){
                    MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                            .questionNumber(record.getQuestionNumber())
                            .question(record.getQuestion())
                            .type(record.getMonthlyRecordType())
                            .answer(record.getAnswer())
                            .build();
                    mentalList.add(monthlyNumberResponseDTO);
                }
                if(record.getMonthlyRecordType() == MonthlyRecordType.SF_ACTIVITY){
                    MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                            .questionNumber(record.getQuestionNumber())
                            .question(record.getQuestion())
                            .type(record.getMonthlyRecordType())
                            .answer(record.getAnswer())
                            .build();
                    activityList.add(monthlyNumberResponseDTO);
                }
                if(record.getMonthlyRecordType() == MonthlyRecordType.SF_DIET){
                    MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                            .questionNumber(record.getQuestionNumber())
                            .question(record.getQuestion())
                            .type(record.getMonthlyRecordType())
                            .answer(record.getAnswer())
                            .build();
                    dietList.add(monthlyNumberResponseDTO);
                }
                if(record.getMonthlyRecordType() == MonthlyRecordType.SF_MEDICATION){
                    MonthlyAnswerResponseDTO monthlyNumberResponseDTO = new MonthlyAnswerResponseDTO().builder()
                            .questionNumber(record.getQuestionNumber())
                            .question(record.getQuestion())
                            .type(record.getMonthlyRecordType())
                            .answer(record.getAnswer())
                            .build();
                    medicineList.add(monthlyNumberResponseDTO);
                }
            }
            float sat_sf_c_activityPoint = (((getDataByQuestionNumber(sat_sf_c_List,1)+
                    getDataByQuestionNumber(sat_sf_c_List,2)+
                    getDataByQuestionNumber(sat_sf_c_List,4))/3)-1)/3*100;
            float sat_sf_c_positivityPoint = (((getDataByQuestionNumber(sat_sf_c_List,5)+
                    getDataByQuestionNumber(sat_sf_c_List,6)+
                    getDataByQuestionNumber(sat_sf_c_List,7)
                    +getDataByQuestionNumber(sat_sf_c_List,8))/4)-1)/3*100;
            float sat_sf_c_supportPoint = (((getDataByQuestionNumber(sat_sf_c_List,3)+
                    getDataByQuestionNumber(sat_sf_c_List,10))/2)-1)/3*100;
            float sat_sf_c_experiencePoint = ((getDataByQuestionNumber(sat_sf_c_List,9)-1))/3*100;

            float sat_sf_p_lifeValue = ((getDataByQuestionNumber(sat_sf_p_List,1)-1))/3*100;
            float sat_sf_p_targetAndAction = (((getDataByQuestionNumber(sat_sf_p_List,2)+
                    getDataByQuestionNumber(sat_sf_p_List,3)+
                    getDataByQuestionNumber(sat_sf_p_List,5))/3)-1)/3*100;
            float sat_sf_p_decision = ((((getDataByQuestionNumber(sat_sf_p_List, 4) +
                    getDataByQuestionNumber(sat_sf_p_List, 10)) / 2) - 1) / 3) * 100;
            float sat_sf_p_buildPlan = ((((getDataByQuestionNumber(sat_sf_p_List, 6) +
                    getDataByQuestionNumber(sat_sf_p_List, 7)) / 2) - 1) / 3) * 100;
            float sat_sf_p_healthyEnvironment = ((((getDataByQuestionNumber(sat_sf_p_List, 8) +
                    getDataByQuestionNumber(sat_sf_p_List, 9)) / 2) - 1) / 3) * 100;

            float sat_sf_i_e_activityPoint = ((getDataByQuestionNumber(sat_sf_i_List,6)-1))/3*100;
            float sat_sf_i_e_activityStressPoint = ((getDataByQuestionNumber(sat_sf_i_List,2)-1))/3*100;
            float sat_sf_i_e_activitySubstantialPoint = ((((getDataByQuestionNumber(sat_sf_i_List, 1) +
                    getDataByQuestionNumber(sat_sf_i_List, 4) +
                    getDataByQuestionNumber(sat_sf_i_List, 5) +
                    getDataByQuestionNumber(sat_sf_i_List, 9)) / 4) - 1) / 3) * 100;
            float sat_sf_i_e_energy = ((getDataByQuestionNumber(sat_sf_i_List,3)-1))/3*100;
            float sat_sf_i_e_motivation= ((((getDataByQuestionNumber(sat_sf_i_List, 7) +
                    getDataByQuestionNumber(sat_sf_i_List, 8)) / 4) - 1) / 3) * 100;
            float sat_sf_i_e_planCheck= ((getDataByQuestionNumber(sat_sf_i_List,10)-1))/3*100;

            float sf_mentalPoint= ((((getDataByQuestionNumber(mentalList, 1) +
                    getDataByQuestionNumber(mentalList, 2) +
                    getDataByQuestionNumber(mentalList, 3) +
                    getDataByQuestionNumber(mentalList, 4) +
                    getDataByQuestionNumber(mentalList, 5)) / 5) - 1) / 3) * 100;

            float sf_activity_planPoint= ((((getDataByQuestionNumber(activityList, 1) +
                    getDataByQuestionNumber(activityList, 2) +
                    getDataByQuestionNumber(activityList, 3)) / 3) - 1) / 3) * 100;
            float sf_activity_habitPoint= ((((getDataByQuestionNumber(activityList, 4) +
                    getDataByQuestionNumber(activityList, 5) ) / 2) - 1) / 3) * 100;

            float sf_diet_healthyPoint= ((((getDataByQuestionNumber(dietList, 1) +
                    getDataByQuestionNumber(dietList, 4) +
                    getDataByQuestionNumber(dietList, 5)) / 3) - 1) / 3) * 100;
            float sf_diet_vegetablePoint= ((((getDataByQuestionNumber(dietList, 3) +
                    getDataByQuestionNumber(dietList, 7)) / 2) - 1) / 3) * 100;
            float sf_diet_habitPoint= ((((getDataByQuestionNumber(dietList, 2) +
                    getDataByQuestionNumber(dietList, 6)) / 2) - 1) / 3) * 100;

            float sf_medicine_followPlanPoint= (((getDataByQuestionNumber(medicineList, 1)) - 1) / 3) * 100;
            float sf_medicine_habitPoint= ((((getDataByQuestionNumber(medicineList, 2) +
                    getDataByQuestionNumber(medicineList, 3) +
                    getDataByQuestionNumber(medicineList, 4)) / 3) - 1) / 3) * 100;



            float sat_sf_c_total= 0;
            float sat_sf_p_total= 0;
            float sat_sf_i_total= 0;
            for (int i = 1; i <= 10 ; i++){
                sat_sf_c_total += getDataByQuestionNumber(sat_sf_c_List,i);
                sat_sf_p_total += getDataByQuestionNumber(sat_sf_p_List,i);
                sat_sf_i_total += getDataByQuestionNumber(sat_sf_i_List,i);
            }
            sat_sf_c_total = (((sat_sf_c_total/10)-1)/3)*100;
            sat_sf_p_total = (((sat_sf_p_total/10)-1)/3)*100;
            sat_sf_i_total = (((sat_sf_i_total/10)-1)/3)*100;
            float finalSATTotal = (sat_sf_c_total+sat_sf_p_total+sat_sf_i_total)/3;

            float sf_mental_modelPoint= ((((getDataByQuestionNumber(mentalList, 1) +
                    getDataByQuestionNumber(mentalList, 2) +
                    getDataByQuestionNumber(mentalList, 3) +
                    getDataByQuestionNumber(mentalList, 4)+
                    getDataByQuestionNumber(mentalList, 5)) / 5) - 1) / 3) * 100;
            float sf_activity_modelPoint= ((((getDataByQuestionNumber(activityList, 1) +
                    getDataByQuestionNumber(activityList, 2) +
                    getDataByQuestionNumber(activityList, 3) +
                    getDataByQuestionNumber(activityList, 4)+
                    getDataByQuestionNumber(activityList, 5)) / 5) - 1) / 3) * 100;
            float sf_diet_modelPoint= ((((getDataByQuestionNumber(dietList, 1) +
                    getDataByQuestionNumber(dietList, 2) +
                    getDataByQuestionNumber(dietList, 3) +
                    getDataByQuestionNumber(dietList, 4)+
                    getDataByQuestionNumber(dietList, 5)+
                    getDataByQuestionNumber(dietList, 6)+
                    getDataByQuestionNumber(dietList, 7)) / 7) - 1) / 3) * 100;
            float sf_medicine_modelPoint= ((((getDataByQuestionNumber(dietList, 1) +
                    getDataByQuestionNumber(dietList, 2) +
                    getDataByQuestionNumber(dietList, 3) +
                    getDataByQuestionNumber(dietList, 4)) / 4) - 1) / 3) * 100;
            float finalSFTotal = (sf_mental_modelPoint+sf_activity_modelPoint+sf_diet_modelPoint+sf_medicine_modelPoint)/4;

            satResponseDTO.setSat_sf_c_activityPoint(sat_sf_c_activityPoint);
            satResponseDTO.setSat_sf_c_positivityPoint(sat_sf_c_positivityPoint);
            satResponseDTO.setSat_sf_c_supportPoint(sat_sf_c_supportPoint);
            satResponseDTO.setSat_sf_c_experiencePoint(sat_sf_c_experiencePoint);
            satResponseDTO.setSat_sf_p_lifeValue(sat_sf_p_lifeValue);
            satResponseDTO.setSat_sf_p_targetAndAction(sat_sf_p_targetAndAction);
            satResponseDTO.setSat_sf_p_decision(sat_sf_p_decision);
            satResponseDTO.setSat_sf_p_buildPlan(sat_sf_p_buildPlan);
            satResponseDTO.setSat_sf_p_healthyEnvironment(sat_sf_p_healthyEnvironment);
            satResponseDTO.setSat_sf_i_e_activityPoint(sat_sf_i_e_activityPoint);
            satResponseDTO.setSat_sf_i_e_activityStressPoint(sat_sf_i_e_activityStressPoint);
            satResponseDTO.setSat_sf_i_e_activitySubstantialPoint(sat_sf_i_e_activitySubstantialPoint);
            satResponseDTO.setSat_sf_i_e_energy(sat_sf_i_e_energy);
            satResponseDTO.setSat_sf_i_e_motivation(sat_sf_i_e_motivation);
            satResponseDTO.setSat_sf_i_e_planCheck(sat_sf_i_e_planCheck);
            satResponseDTO.setSat_sf_c_total(sat_sf_c_total);
            satResponseDTO.setSat_sf_p_total(sat_sf_p_total);
            satResponseDTO.setSat_sf_i_total(sat_sf_i_total);
            satResponseDTO.setTotal(finalSATTotal);

            sfResponseDTO.setSf_mentalPoint(sf_mentalPoint);
            sfResponseDTO.setSf_activity_planPoint(sf_activity_planPoint);
            sfResponseDTO.setSf_activity_habitPoint(sf_activity_habitPoint);
            sfResponseDTO.setSf_diet_healthyPoint(sf_diet_healthyPoint);
            sfResponseDTO.setSf_diet_vegetablePoint(sf_diet_vegetablePoint);
            sfResponseDTO.setSf_diet_habitPoint(sf_diet_habitPoint);
            sfResponseDTO.setSf_medicine_followPlanPoint(sf_medicine_followPlanPoint);
            sfResponseDTO.setSf_medicine_habitPoint(sf_medicine_habitPoint);
            sfResponseDTO.setSf_mental_modelPoint(sf_mental_modelPoint);
            sfResponseDTO.setSf_activity_modelPoint(sf_activity_modelPoint);
            sfResponseDTO.setSf_diet_modelPoint(sf_diet_modelPoint);
            sfResponseDTO.setSf_medicine_modelPoint(sf_medicine_modelPoint);
            sfResponseDTO.setTotal(finalSFTotal);

            MonthlyStatisticResponseDTO monthlyStatisticResponseDTO = new MonthlyStatisticResponseDTO()
                    .builder()
//                    .month(monthNumber)
                    .sfResponseDTO(sfResponseDTO).satResponseDTO(satResponseDTO).build();
            return monthlyStatisticResponseDTO;
        }
        return new MonthlyStatisticResponseDTO().builder()
//                .month(monthNumber)
                .sfResponseDTO(sfResponseDTO).satResponseDTO(satResponseDTO).build();
    }

//    @Override
    public List<MonthlyStatisticResponseDTO> getPoint3Month(Integer appUserId) {
        List<MonthlyStatisticResponseDTO> monthlyStatisticResponseDTOList = new ArrayList<>();
        List<Integer> monthlyNumbers = monthlyQuestionRepository.find3ByAppUser(appUserId);
        for (Integer month : monthlyNumbers){
            if(month != 0){
                monthlyStatisticResponseDTOList.add(getPoint(month,appUserId));
            }
        }

        return monthlyStatisticResponseDTOList;
    }

    @Override
    public MobileGeneralChartResponseDTO getPoint3MonthMobile() {
        MobileGeneralChartResponseDTO mobileGeneralChartResponseDTO= new MobileGeneralChartResponseDTO();
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        mobileGeneralChartResponseDTO.setChart3Month(getPoint3Month(appUser.getId()));
        mobileGeneralChartResponseDTO.setFirstWeek(getPoint(0,appUser.getId()));
        return mobileGeneralChartResponseDTO;
    }
    @Override
    public List<Integer> getList3MonthlyNumberWeb(Integer appUserId) {
        List<Integer> monthlyNumberResponseDTOList = new ArrayList<>();
        List<Integer> monthlyNumbers = monthlyQuestionRepository.find3ByAppUserWeb(appUserId);
        for (Integer monthNumber : monthlyNumbers) {
            monthlyNumberResponseDTOList.add(monthNumber);
        }
        return monthlyNumberResponseDTOList;
    }


//    @Override
    public List<MonthlyStatisticResponseDTO> getPoint2Month(Integer appUserId, Integer monthNumber) {
        List<Integer> monthlyNumbers = monthlyQuestionRepository.find2ByAppUserAndMonthNumber(appUserId,monthNumber);
        List<MonthlyStatisticResponseDTO> monthlyStatisticResponseDTOList = new ArrayList<>();
        for (Integer month : monthlyNumbers){
            monthlyStatisticResponseDTOList.add(getPoint(month,appUserId));
        }
        return monthlyStatisticResponseDTOList;
    }
    @Override
    public List<MonthlyStatisticResponseDTO> getPoint2MonthMobile(Integer monthNumber) {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        return getPoint2Month(appUser.getId(),monthNumber);
    }
    @Override
    public List<MonthlyStatisticResponseDTO> getPoint2MonthWeb(Integer appUserId, Integer monthNumber) {
        return getPoint2Month(appUserId,monthNumber);
    }
    @Override
    public List<MonthlyStatisticResponseDTO> getPoint12MonthWeb(Integer appUserId) {
        List<Integer> monthlyNumbers = monthlyQuestionRepository.find12ByAppUser(appUserId);
        List<MonthlyStatisticResponseDTO> monthlyStatisticResponseDTOList = new ArrayList<>();
        for (Integer month : monthlyNumbers){
            monthlyStatisticResponseDTOList.add(getPoint(month,appUserId));
        }
        return monthlyStatisticResponseDTOList;
    }



    public Float getDataByQuestionNumber(List<MonthlyAnswerResponseDTO> list,int questionNumber){
        Optional<MonthlyAnswerResponseDTO> monthlyAnswerResponseDTO = list.stream().filter(record -> record.getQuestionNumber() == questionNumber).findFirst();
        return (float) (monthlyAnswerResponseDTO.isPresent() ? monthlyAnswerResponseDTO.get().getAnswer() : 1f);
    }



}