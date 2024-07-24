package vn.edu.fpt.SmartHealthC.utils;

import org.springframework.stereotype.Component;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MobileGeneralChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyStatisticResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.SAT.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.*;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.MonthlyStatisticScreenTotalResponseDTO;

import java.util.ArrayList;
import java.util.List;


@Component
public class FormatDTOUtils {
    public static MobileScreenTotalChartResponseDTO formatMobileScreenTotal(MobileGeneralChartResponseDTO mobileGeneralChartResponseDTO){
        MobileScreenTotalChartResponseDTO mobileScreenTotalChartResponseDTO = new MobileScreenTotalChartResponseDTO();
        //First Week
        SatScreenTotalResponseDTO satScreenTotalResponseDTO = new SatScreenTotalResponseDTO().builder()
                .sat_sf_c_total(mobileGeneralChartResponseDTO.getFirstWeek().getSatResponseDTO().getSat_sf_c_total())
                .sat_sf_p_total(mobileGeneralChartResponseDTO.getFirstWeek().getSatResponseDTO().getSat_sf_p_total())
                .sat_sf_i_total(mobileGeneralChartResponseDTO.getFirstWeek().getSatResponseDTO().getSat_sf_i_total())
                .build();
        SFScreenTotalResponseDTO sfScreenTotalResponseDTO = new SFScreenTotalResponseDTO().builder()
                .sf_mental_modelPoint(mobileGeneralChartResponseDTO.getFirstWeek().getSfResponseDTO().getSf_mental_modelPoint())
                .sf_activity_modelPoint(mobileGeneralChartResponseDTO.getFirstWeek().getSfResponseDTO().getSf_activity_modelPoint())
                .sf_diet_modelPoint(mobileGeneralChartResponseDTO.getFirstWeek().getSfResponseDTO().getSf_diet_modelPoint())
                .sf_medicine_modelPoint(mobileGeneralChartResponseDTO.getFirstWeek().getSfResponseDTO().getSf_medicine_modelPoint())
                .build();
        MonthlyStatisticScreenTotalResponseDTO firstWeek = new MonthlyStatisticScreenTotalResponseDTO().builder()
                .month(mobileGeneralChartResponseDTO.getFirstWeek().getMonth())
        .satResponseDTO(satScreenTotalResponseDTO).sfResponseDTO(sfScreenTotalResponseDTO)
        .build();
        mobileScreenTotalChartResponseDTO.setFirstWeek(firstWeek);
        //List 3 month
        List<MonthlyStatisticScreenTotalResponseDTO> chart3Month = new ArrayList<>();
        for(MonthlyStatisticResponseDTO record : mobileGeneralChartResponseDTO.getChart3Month()){
            SatScreenTotalResponseDTO satItemScreenTotalResponseDTO = new SatScreenTotalResponseDTO().builder()
                    .sat_sf_c_total(record.getSatResponseDTO().getSat_sf_c_total())
                    .sat_sf_p_total(record.getSatResponseDTO().getSat_sf_p_total())
                    .sat_sf_i_total(record.getSatResponseDTO().getSat_sf_i_total())
                    .build();
            SFScreenTotalResponseDTO sfSItemScreenTotalResponseDTO = new SFScreenTotalResponseDTO().builder()
                    .sf_mental_modelPoint(record.getSfResponseDTO().getSf_mental_modelPoint())
                    .sf_activity_modelPoint(record.getSfResponseDTO().getSf_activity_modelPoint())
                    .sf_diet_modelPoint(record.getSfResponseDTO().getSf_diet_modelPoint())
                    .sf_medicine_modelPoint(record.getSfResponseDTO().getSf_medicine_modelPoint())
                    .build();
            MonthlyStatisticScreenTotalResponseDTO itemList3Month = new MonthlyStatisticScreenTotalResponseDTO().builder()
                    .month(record.getMonth())
                    .satResponseDTO(satItemScreenTotalResponseDTO).sfResponseDTO(sfSItemScreenTotalResponseDTO)
                    .build();
            chart3Month.add(itemList3Month);
        }
        mobileScreenTotalChartResponseDTO.setChart3Month(chart3Month);
    return mobileScreenTotalChartResponseDTO;
    }

    public static List<MonthlyStatisticScreenTotalSFResponseDTO> formatWebScreenTotalSF(List<MonthlyStatisticResponseDTO> data){
        //List 12 month
        List<MonthlyStatisticScreenTotalSFResponseDTO> chart12Month = new ArrayList<>();
        for(MonthlyStatisticResponseDTO record : data){
            SFScreenTotalResponseDTO sfSItemScreenTotalResponseDTO = new SFScreenTotalResponseDTO().builder()
                    .sf_mental_modelPoint(record.getSfResponseDTO().getSf_mental_modelPoint())
                    .sf_activity_modelPoint(record.getSfResponseDTO().getSf_activity_modelPoint())
                    .sf_diet_modelPoint(record.getSfResponseDTO().getSf_diet_modelPoint())
                    .sf_medicine_modelPoint(record.getSfResponseDTO().getSf_medicine_modelPoint())
                    .build();
            MonthlyStatisticScreenTotalSFResponseDTO itemList3Month = new MonthlyStatisticScreenTotalSFResponseDTO().builder()
                    .month(record.getMonth())
                    .sfResponseDTO(sfSItemScreenTotalResponseDTO)
                    .build();
            chart12Month.add(itemList3Month);
        }
        return chart12Month;
    }
    public static List<MonthlyStatisticScreenTotalSATResponseDTO> formatWebScreenTotalSAT(List<MonthlyStatisticResponseDTO> data){
        //List 12 month
        List<MonthlyStatisticScreenTotalSATResponseDTO> chart12Month = new ArrayList<>();
        for(MonthlyStatisticResponseDTO record : data){
            SatScreenTotalResponseDTO satItemScreenTotalResponseDTO = new SatScreenTotalResponseDTO().builder()
                    .sat_sf_c_total(record.getSatResponseDTO().getSat_sf_c_total())
                    .sat_sf_p_total(record.getSatResponseDTO().getSat_sf_p_total())
                    .sat_sf_i_total(record.getSatResponseDTO().getSat_sf_i_total())
                    .build();
            MonthlyStatisticScreenTotalSATResponseDTO itemList3Month = new MonthlyStatisticScreenTotalSATResponseDTO().builder()
                    .month(record.getMonth())
                    .satResponseDTO(satItemScreenTotalResponseDTO)
                    .build();
            chart12Month.add(itemList3Month);
        }
        return chart12Month;
    }
    public static List<MobileScreen2MonthChartSATUpgradeResponseDTO> formatWebScreen2MonthSAT(List<MonthlyStatisticResponseDTO> data){
        //List 2 month
        List<MobileScreen2MonthChartSATUpgradeResponseDTO> list2MonthNonData = new ArrayList<>();
        if(!data.isEmpty()){
            List<MobileScreen2MonthChartSATUpgradeResponseDTO> list2Month = new ArrayList<>();
            //Screen1
            List<ScreenResponseDTO> listScreen1 = new ArrayList<>();
            listScreen1.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_c_total())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_c_total())
                    .build());
            listScreen1.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_p_total())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_p_total())
                    .build());
            listScreen1.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_i_total())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_i_total())
                    .build());
            list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                    .screenNumber(1)
                    .list(listScreen1)
                    .build());
            //Screen2
            List<ScreenResponseDTO> listScreen2 = new ArrayList<>();
            listScreen2.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_c_activityPoint())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_c_activityPoint())
                    .build());
            listScreen2.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_c_positivityPoint())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_c_positivityPoint())
                    .build());
            listScreen2.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_c_supportPoint())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_c_supportPoint())
                    .build());
            listScreen2.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_c_experiencePoint())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_c_experiencePoint())
                    .build());
            list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                    .screenNumber(2)
                    .list(listScreen2)
                    .build());
            //Screen3
            List<ScreenResponseDTO> listScreen3 = new ArrayList<>();
            listScreen3.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_p_lifeValue())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_p_lifeValue())
                    .build());
            listScreen3.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_p_targetAndAction())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_p_targetAndAction())
                    .build());
            listScreen3.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_p_decision())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_p_decision())
                    .build());
            listScreen3.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_p_buildPlan())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_p_buildPlan())
                    .build());
            listScreen3.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_p_healthyEnvironment())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_p_healthyEnvironment())
                    .build());
            list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                    .screenNumber(3)
                    .list(listScreen3)
                    .build());
            //Screen4
            List<ScreenResponseDTO> listScreen4 = new ArrayList<>();
            listScreen4.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_i_e_activityPoint())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_i_e_activityPoint())
                    .build());
            listScreen4.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_i_e_activityStressPoint())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_i_e_activityStressPoint())
                    .build());
            listScreen4.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_i_e_activitySubstantialPoint())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_i_e_activitySubstantialPoint())
                    .build());
            listScreen4.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_i_e_energy())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_i_e_energy())
                    .build());
            listScreen4.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_i_e_motivation())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_i_e_motivation())
                    .build());
            listScreen4.add(new ScreenResponseDTO().builder()
                    .left(data.get(0).getSatResponseDTO().getSat_sf_i_e_planCheck())
                    .right(data.get(1).getSatResponseDTO().getSat_sf_i_e_planCheck())
                    .build());
            list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                    .screenNumber(4)
                    .list(listScreen4)
                    .build());
            return list2Month;
        }else{
            return list2MonthNonData;
        }

    }
    public static List<MobileScreen2MonthChartSATUpgradeResponseDTO> formatWebScreen2MonthSF(List<MonthlyStatisticResponseDTO> data){
       if(!data.isEmpty()){
           //List 2 month
           List<MobileScreen2MonthChartSATUpgradeResponseDTO> list2Month = new ArrayList<>();
           //Screen1
           List<ScreenResponseDTO> listScreen1 = new ArrayList<>();
           listScreen1.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_mental_modelPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_mental_modelPoint())
                   .build());
           listScreen1.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_activity_habitPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_activity_habitPoint())
                   .build());
           listScreen1.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_diet_modelPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_diet_modelPoint())
                   .build());
           listScreen1.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_medicine_modelPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_medicine_modelPoint())
                   .build());
           list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                   .screenNumber(1)
                   .list(listScreen1)
                   .build());
           //Screen2
           List<ScreenResponseDTO> listScreen2 = new ArrayList<>();
           listScreen2.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_mentalPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_mentalPoint())
                   .build());
           list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                   .screenNumber(2)
                   .list(listScreen2)
                   .build());
           //Screen3
           List<ScreenResponseDTO> listScreen3 = new ArrayList<>();
           listScreen3.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_activity_planPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_activity_planPoint())
                   .build());
           listScreen3.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_activity_habitPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_activity_habitPoint())
                   .build());
           list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                   .screenNumber(3)
                   .list(listScreen3)
                   .build());
           //Screen4
           List<ScreenResponseDTO> listScreen4 = new ArrayList<>();
           listScreen4.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_diet_healthyPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_diet_healthyPoint())
                   .build());
           listScreen4.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_diet_vegetablePoint())
                   .right(data.get(1).getSfResponseDTO().getSf_diet_vegetablePoint())
                   .build());
           listScreen4.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_diet_habitPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_diet_habitPoint())
                   .build());
           list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                   .screenNumber(4)
                   .list(listScreen4)
                   .build());
           //Screen5
           List<ScreenResponseDTO> listScreen5 = new ArrayList<>();
           listScreen5.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_medicine_followPlanPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_medicine_followPlanPoint())
                   .build());
           listScreen5.add(new ScreenResponseDTO().builder()
                   .left(data.get(0).getSfResponseDTO().getSf_medicine_habitPoint())
                   .right(data.get(1).getSfResponseDTO().getSf_medicine_habitPoint())
                   .build());

           list2Month.add(new MobileScreen2MonthChartSATUpgradeResponseDTO().builder()
                   .screenNumber(5)
                   .list(listScreen5)
                   .build());
           return list2Month;
       }else{
           List<MobileScreen2MonthChartSATUpgradeResponseDTO> list2MonthNonData = new ArrayList<>();
           return list2MonthNonData;
       }
    }
    public static List<MobileScreen2MonthChartSATResponseDTO> formatMobileScreen2MonthSAT(List<MonthlyStatisticResponseDTO> data){
        //List 2 month
        List<MobileScreen2MonthChartSATResponseDTO> chart2Month = new ArrayList<>();
        for(MonthlyStatisticResponseDTO record : data){
            SatScreen2MonthResponseDTO satItemScreen2MonthResponseDTO = new SatScreen2MonthResponseDTO().builder()
                    .sat_sf_c_total(record.getSatResponseDTO().getSat_sf_c_total())
                    .sat_sf_p_total(record.getSatResponseDTO().getSat_sf_p_total())
                    .sat_sf_i_total(record.getSatResponseDTO().getSat_sf_i_total())
                    .sat_sf_c_activityPoint(record.getSatResponseDTO().getSat_sf_c_activityPoint())
                    .sat_sf_c_positivityPoint(record.getSatResponseDTO().getSat_sf_c_positivityPoint())
                    .sat_sf_c_supportPoint(record.getSatResponseDTO().getSat_sf_c_supportPoint())
                    .sat_sf_c_experiencePoint(record.getSatResponseDTO().getSat_sf_c_experiencePoint())
                    .sat_sf_p_lifeValue(record.getSatResponseDTO().getSat_sf_p_lifeValue())
                    .sat_sf_p_targetAndAction(record.getSatResponseDTO().getSat_sf_p_targetAndAction())
                    .sat_sf_p_decision(record.getSatResponseDTO().getSat_sf_p_decision())
                    .sat_sf_p_buildPlan(record.getSatResponseDTO().getSat_sf_p_buildPlan())
                    .sat_sf_p_healthyEnvironment(record.getSatResponseDTO().getSat_sf_p_healthyEnvironment())
                    .sat_sf_i_e_activityPoint(record.getSatResponseDTO().getSat_sf_i_e_activityPoint())
                    .sat_sf_i_e_activityStressPoint(record.getSatResponseDTO().getSat_sf_i_e_activityStressPoint())
                    .sat_sf_i_e_activitySubstantialPoint(record.getSatResponseDTO().getSat_sf_i_e_activitySubstantialPoint())
                    .sat_sf_i_e_energy(record.getSatResponseDTO().getSat_sf_i_e_energy())
                    .sat_sf_i_e_motivation(record.getSatResponseDTO().getSat_sf_i_e_motivation())
                    .sat_sf_i_e_planCheck(record.getSatResponseDTO().getSat_sf_i_e_planCheck())
                    .build();

            MobileScreen2MonthChartSATResponseDTO itemList3Month = new MobileScreen2MonthChartSATResponseDTO().builder()
                    .month(record.getMonth())
                    .satResponseDTO(satItemScreen2MonthResponseDTO)
                    .build();
            chart2Month.add(itemList3Month);
        }
        return chart2Month;
    }
    public static List<MobileScreen2MonthChartSFResponseDTO> formatMobileScreen2MonthSF(List<MonthlyStatisticResponseDTO> data){
        //List 2 month
        List<MobileScreen2MonthChartSFResponseDTO> chart2Month = new ArrayList<>();
        for(MonthlyStatisticResponseDTO record : data){
            SFScreen2MonthResponseDTO sfSItemScreen2MonthResponseDTO = new SFScreen2MonthResponseDTO().builder()
                    .sf_mental_modelPoint(record.getSfResponseDTO().getSf_mental_modelPoint())
                    .sf_activity_modelPoint(record.getSfResponseDTO().getSf_activity_modelPoint())
                    .sf_diet_modelPoint(record.getSfResponseDTO().getSf_diet_modelPoint())
                    .sf_medicine_modelPoint(record.getSfResponseDTO().getSf_medicine_modelPoint())
                    .sf_mentalPoint(record.getSfResponseDTO().getSf_mentalPoint())
                    .sf_activity_planPoint(record.getSfResponseDTO().getSf_activity_planPoint())
                    .sf_activity_habitPoint(record.getSfResponseDTO().getSf_activity_habitPoint())
                    .sf_diet_healthyPoint(record.getSfResponseDTO().getSf_diet_healthyPoint())
                    .sf_diet_vegetablePoint(record.getSfResponseDTO().getSf_diet_vegetablePoint())
                    .sf_diet_habitPoint(record.getSfResponseDTO().getSf_diet_habitPoint())
                    .sf_medicine_followPlanPoint(record.getSfResponseDTO().getSf_medicine_followPlanPoint())
                    .sf_medicine_habitPoint(record.getSfResponseDTO().getSf_medicine_habitPoint())
                    .build();
            MobileScreen2MonthChartSFResponseDTO itemList3Month = new MobileScreen2MonthChartSFResponseDTO().builder()
                    .month(record.getMonth())
                    .sfResponseDTO(sfSItemScreen2MonthResponseDTO)
                    .build();
            chart2Month.add(itemList3Month);
        }
        return chart2Month;
    }
}
