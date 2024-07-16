package vn.edu.fpt.SmartHealthC.utils;

import org.springframework.stereotype.Component;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MobileGeneralChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyStatisticResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.MobileScreen2MonthChartSATResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.MobileScreen2MonthChartSFResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.SFScreen2MonthResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.SatScreen2MonthResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.MobileScreenTotalChartResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.MonthlyStatisticScreenTotalResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.SFScreenTotalResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal.SatScreenTotalResponseDTO;

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
//                .month(mobileGeneralChartResponseDTO.getFirstWeek().getMonth())
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
//                    .month(record.getMonth())
                    .satResponseDTO(satItemScreenTotalResponseDTO).sfResponseDTO(sfSItemScreenTotalResponseDTO)
                    .build();
            chart3Month.add(itemList3Month);
        }
        mobileScreenTotalChartResponseDTO.setChart3Month(chart3Month);
    return mobileScreenTotalChartResponseDTO;
    }

    public static List<MonthlyStatisticScreenTotalResponseDTO> formatWebScreenTotal(List<MonthlyStatisticResponseDTO> data){
        //List 12 month
        List<MonthlyStatisticScreenTotalResponseDTO> chart12Month = new ArrayList<>();
        for(MonthlyStatisticResponseDTO record : data){
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
//                    .month(record.getMonth())
                    .satResponseDTO(satItemScreenTotalResponseDTO).sfResponseDTO(sfSItemScreenTotalResponseDTO)
                    .build();
            chart12Month.add(itemList3Month);
        }
        return chart12Month;
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
//                    .month(record.getMonth())
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
//                    .month(record.getMonth())
                    .sfResponseDTO(sfSItemScreen2MonthResponseDTO)
                    .build();
            chart2Month.add(itemList3Month);
        }
        return chart2Month;
    }
}
