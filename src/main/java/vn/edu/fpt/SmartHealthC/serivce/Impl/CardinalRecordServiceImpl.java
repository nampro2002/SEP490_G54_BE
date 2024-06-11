package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeCardinalIndex;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;
import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.CardinalRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.CardinalRecordService;

import java.util.*;

@Service
public class CardinalRecordServiceImpl implements CardinalRecordService {

    @Autowired
    private CardinalRecordRepository CardinalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public CardinalRecord createCardinalRecord(CardinalRecordDTO CardinalRecordDTO)
    {
        CardinalRecord cardinalRecord =  CardinalRecord.builder()
                .value(CardinalRecordDTO.getValue())
                .weekStart(CardinalRecordDTO.getWeekStart())
                .date(CardinalRecordDTO.getDate())
                .typeCardinalIndex(CardinalRecordDTO.getTypeCardinalIndex())
                .timeMeasure(CardinalRecordDTO.getTimeMeasure()).build();
        Optional<AppUser> appUser = appUserRepository.findById(CardinalRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        cardinalRecord.setAppUserId(appUser.get());
        return  CardinalRecordRepository.save(cardinalRecord);
    }

    @Override
    public CardinalRecord getCardinalRecordById(Integer id) {
        Optional<CardinalRecord> CardinalRecord = CardinalRecordRepository.findById(id);
        if(CardinalRecord.isEmpty()) {
            throw new AppException(ErrorCode.CARDINAL_NOT_FOUND);
        }
        return CardinalRecord.get();
    }

    @Override
    public List<CardinalRecordResponseDTO> getAllCardinalRecords(Integer userId) {

        List<Date> cardinalWeekList = CardinalRecordRepository.findDistinctWeek(userId);
        List<CardinalRecordResponseDTO> responseDTOList = new ArrayList<>();
        for (Date week : cardinalWeekList) {
            CardinalRecordResponseDTO cardinalRecordResponseDTO = CardinalRecordResponseDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(cardinalRecordResponseDTO);
        }

        for (CardinalRecordResponseDTO record : responseDTOList) {
            List<CardinalRecord> cardinalRecords = CardinalRecordRepository.findByWeekStart(record.getWeekStart());
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
                //sortby getTimeMeasure getIndex
                recordPerDayList.sort(Comparator.comparingInt(o -> TypeTimeMeasure.getIndex(o.getTimeMeasure())));
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
//        List<CardinalRecord> cardinalRecords = CardinalRecordRepository.findByAppUserId();
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
    public List<CardinalRecord> getAllCardinalRecordsVip() {
        return CardinalRecordRepository.findAll();
    }


    @Override
    public CardinalRecord updateCardinalRecord(Integer id, CardinalRecordDTO CardinalRecordDTO) {
        CardinalRecord cardinalRecord = getCardinalRecordById(id);
        cardinalRecord.setValue(CardinalRecordDTO.getValue());
        cardinalRecord.setWeekStart(CardinalRecordDTO.getWeekStart());
        cardinalRecord.setDate(CardinalRecordDTO.getDate());
        cardinalRecord.setTypeCardinalIndex(CardinalRecordDTO.getTypeCardinalIndex());
        cardinalRecord.setTimeMeasure(CardinalRecordDTO.getTimeMeasure());
        return  CardinalRecordRepository.save(cardinalRecord);
    }

    @Override
    public CardinalRecord deleteCardinalRecord(Integer id) {
        CardinalRecord cardinalRecord = getCardinalRecordById(id);
        CardinalRecordRepository.deleteById(id);
        return cardinalRecord;
    }


}