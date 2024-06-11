package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeCardinalIndex;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NumeralRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.NumeralRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.NumeralRecordService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NumeralRecordServiceImpl implements NumeralRecordService {

    @Autowired
    private NumeralRecordRepository numeralRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public CardinalRecord createNumeralRecord(NumeralRecordDTO numeralRecordDTO)
    {
        CardinalRecord cardinalRecord =  CardinalRecord.builder()
                .value(numeralRecordDTO.getValue())
                .weekStart(numeralRecordDTO.getWeekStart())
                .date(numeralRecordDTO.getDate())
                .typeCardinalIndex(numeralRecordDTO.getTypeCardinalIndex())
                .timeMeasure(numeralRecordDTO.getTimeMeasure()).build();
        Optional<AppUser> appUser = appUserRepository.findById(numeralRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        cardinalRecord.setAppUserId(appUser.get());
        return  numeralRecordRepository.save(cardinalRecord);
    }

    @Override
    public CardinalRecord getNumeralRecordById(Integer id) {
        Optional<CardinalRecord> numeralRecord = numeralRecordRepository.findById(id);
        if(numeralRecord.isEmpty()) {
            throw new AppException(ErrorCode.NUMERAL_NOT_FOUND);
        }
        return numeralRecord.get();
    }

    @Override
    public List<CardinalRecordResponseDTO> getAllNumeralRecords(Integer userId) {

        List<Date> cardinalWeekList = numeralRecordRepository.findDistinctWeek(userId);
        List<CardinalRecordResponseDTO> responseDTOList = new ArrayList<>();
        for (Date week : cardinalWeekList) {
            CardinalRecordResponseDTO cardinalRecordResponseDTO = CardinalRecordResponseDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(cardinalRecordResponseDTO);
        }

        for (CardinalRecordResponseDTO record : responseDTOList) {
            List<CardinalRecord> cardinalRecords = numeralRecordRepository.findByWeekStart(record.getWeekStart());
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            Float avgCholesterol = 0f;
            Float avgHBA1C = 0f;
            Float avgBloodSugar = 0f;
            int countCholesterol = 0;
            int countHBA1C = 0;
            int countBloodSugar = 0;
            for (CardinalRecord cardinalRecord : cardinalRecords) {
                RecordPerDay recordPerDay = RecordPerDay.builder()
                        .date(cardinalRecord.getDate())
                        .timeMeasure(cardinalRecord.getTimeMeasure())
                        .Cholesterol(cardinalRecord.getValue())
                        .HBA1C(cardinalRecord.getValue())
                        .BloodSugar(cardinalRecord.getValue()).build();
                recordPerDayList.add(recordPerDay);
                if (cardinalRecord.getTypeCardinalIndex().equals(TypeCardinalIndex.Cholesterol) ) {
                    if(cardinalRecord.getValue() != null){
                        avgCholesterol += cardinalRecord.getValue();
                        countCholesterol++;
                    }
                }
                if (cardinalRecord.getTypeCardinalIndex().equals(TypeCardinalIndex.HBA1CRecord)) {
                    if(cardinalRecord.getValue() != null){
                        avgHBA1C += cardinalRecord.getValue();
                        countHBA1C++;
                    }
                }
                if (cardinalRecord.getTypeCardinalIndex().equals(TypeCardinalIndex.BloodSugar)) {
                    if(cardinalRecord.getValue() != null){
                        avgBloodSugar += cardinalRecord.getValue();
                        countBloodSugar++;
                    }
                }
            }
            if (countCholesterol != 0) {
                avgCholesterol = avgCholesterol / countCholesterol;
            }
            if (countHBA1C != 0) {
                avgHBA1C = avgHBA1C / countHBA1C;
            }
            if (countBloodSugar != 0) {
                avgBloodSugar = avgBloodSugar / countBloodSugar;
            }
            record.setAvgValue("주간평균:  " + avgHBA1C + "%" + avgCholesterol + "mg/DL" + avgBloodSugar +"mg/DL");
            record.setRecordPerDayList(recordPerDayList);
        }

        return responseDTOList;
    }

    @Override
    public List<CardinalRecordResponseDTO> getAllCardinalRecordsByAppUserId(Integer id) {
//        List<CardinalRecord> cardinalRecords = numeralRecordRepository.findByAppUserId();
        List<CardinalRecordResponseDTO> responseDTOList = new ArrayList<>();
//        for (CardinalRecord cardinalRecord : cardinalRecords) {
//            CardinalRecordResponseDTO cardinalRecordResponseDTO = CardinalRecordResponseDTO.builder()
//                    .appUserId(cardinalRecord.getAppUserId())
//                    .weekStart(cardinalRecord.getWeekStart())
//                    .date(cardinalRecord.getDate())
//                    .timeMeasure(cardinalRecord.getTimeMeasure())
//                    .typeCardinalIndex(cardinalRecord.getTypeCardinalIndex())
//                    .value(cardinalRecord.getValue())
//                    .build();
//            responseDTOList.add(cardinalRecordResponseDTO);
//        }

        return responseDTOList;
    }

    @Override
    public List<CardinalRecord> getAllNumeralRecordsVip() {
        return numeralRecordRepository.findAll();
    }


    @Override
    public CardinalRecord updateNumeralRecord(Integer id, NumeralRecordDTO numeralRecordDTO) {
        CardinalRecord cardinalRecord = getNumeralRecordById(id);
        cardinalRecord.setValue(numeralRecordDTO.getValue());
        cardinalRecord.setWeekStart(numeralRecordDTO.getWeekStart());
        cardinalRecord.setDate(numeralRecordDTO.getDate());
        cardinalRecord.setTypeCardinalIndex(numeralRecordDTO.getTypeCardinalIndex());
        cardinalRecord.setTimeMeasure(numeralRecordDTO.getTimeMeasure());
        return  numeralRecordRepository.save(cardinalRecord);
    }

    @Override
    public CardinalRecord deleteNumeralRecord(Integer id) {
        CardinalRecord cardinalRecord = getNumeralRecordById(id);
        numeralRecordRepository.deleteById(id);
        return cardinalRecord;
    }


}