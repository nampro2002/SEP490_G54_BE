package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.CardinalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.RecordPerDay;
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
    private CardinalRecordRepository cardinalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public CardinalRecord createCardinalRecord(CardinalRecordDTO CardinalRecordDTO) {
        CardinalRecord cardinalRecord = CardinalRecord.builder()
                .Cholesterol(CardinalRecordDTO.getCholesterol())
                .BloodSugar(CardinalRecordDTO.getBloodSugar())
                .HBA1C(CardinalRecordDTO.getHba1c())
                .weekStart(CardinalRecordDTO.getWeekStart())
                .date(CardinalRecordDTO.getDate())
                .timeMeasure(CardinalRecordDTO.getTimeMeasure()).build();
        Optional<AppUser> appUser = appUserRepository.findById(CardinalRecordDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        cardinalRecord.setAppUserId(appUser.get());
        return cardinalRecordRepository.save(cardinalRecord);
    }

    @Override
    public CardinalRecord getCardinalRecordById(Integer id) {
        Optional<CardinalRecord> CardinalRecord = cardinalRecordRepository.findById(id);
        if (CardinalRecord.isEmpty()) {
            throw new AppException(ErrorCode.CARDINAL_NOT_FOUND);
        }
        return CardinalRecord.get();
    }

    @Override
    public List<CardinalRecordResponseDTO> getAllCardinalRecords(Integer userId) {

        List<Date> cardinalWeekList = cardinalRecordRepository.findDistinctWeek(userId);
        List<CardinalRecordResponseDTO> responseDTOList = new ArrayList<>();
        for (Date week : cardinalWeekList) {
            CardinalRecordResponseDTO cardinalRecordResponseDTO = CardinalRecordResponseDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(cardinalRecordResponseDTO);
        }

        for (CardinalRecordResponseDTO record : responseDTOList) {
            List<CardinalRecord> cardinalRecords = cardinalRecordRepository.findByWeekStart(record.getWeekStart(), userId);
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
                        .Cholesterol(cardinalRecord.getCholesterol()+"mg/DL")
                        .HBA1C(cardinalRecord.getHBA1C()+"%")
                        .BloodSugar(cardinalRecord.getBloodSugar()+"mg/DL")
                        .build();
                recordPerDayList.add(recordPerDay);
                //sortby getTimeMeasure getIndex and Date date;
                recordPerDayList.sort(Comparator.comparing(RecordPerDay::getDate).thenComparing(RecordPerDay::getTimeMeasure));

                if (cardinalRecord.getCholesterol() != null) {
                    avgCholesterol += cardinalRecord.getCholesterol();
                    countCholesterol++;
                }
                if (cardinalRecord.getHBA1C() != null) {
                    avgHBA1C += cardinalRecord.getHBA1C();
                    countHBA1C++;
                }
                if (cardinalRecord.getBloodSugar() != null) {
                    avgBloodSugar += cardinalRecord.getBloodSugar();
                    countBloodSugar++;
                }
            }
            if (countCholesterol != 0) {
                avgCholesterol = avgCholesterol / countCholesterol;
                avgCholesterol = (float) (Math.round(avgCholesterol * 100) / 100);
            }
            if (countHBA1C != 0) {
                avgHBA1C = avgHBA1C / countHBA1C;
                avgHBA1C = (float) (Math.round(avgHBA1C * 100) / 100);
            }
            if (countBloodSugar != 0) {
                avgBloodSugar = avgBloodSugar / countBloodSugar;
                avgBloodSugar = (float) (Math.round(avgBloodSugar * 100) / 100);
            }
            record.setAvgValue("주간평균:  " + avgHBA1C + "% / " + avgCholesterol + " mg/DL /  " + avgBloodSugar + "mg/DL");
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
        return cardinalRecordRepository.findAll();
    }


    @Override
    public CardinalRecord updateCardinalRecord(Integer id, CardinalRecordDTO CardinalRecordDTO) {
        CardinalRecord cardinalRecord = getCardinalRecordById(id);
        cardinalRecord.setCholesterol(CardinalRecordDTO.getCholesterol());
        cardinalRecord.setBloodSugar(CardinalRecordDTO.getBloodSugar());
        cardinalRecord.setHBA1C(CardinalRecordDTO.getHba1c());
        cardinalRecord.setWeekStart(CardinalRecordDTO.getWeekStart());
        cardinalRecord.setDate(CardinalRecordDTO.getDate());
        cardinalRecord.setTimeMeasure(CardinalRecordDTO.getTimeMeasure());
        return cardinalRecordRepository.save(cardinalRecord);
    }

    @Override
    public CardinalRecord deleteCardinalRecord(Integer id) {
        CardinalRecord cardinalRecord = getCardinalRecordById(id);
        cardinalRecordRepository.deleteById(id);
        return cardinalRecord;
    }


}