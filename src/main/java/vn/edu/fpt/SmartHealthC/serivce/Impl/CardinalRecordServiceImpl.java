package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;
import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO.BloodPressureResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO.*;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.CardinalRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.CardinalRecordService;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardinalRecordServiceImpl implements CardinalRecordService {

    @Autowired
    private CardinalRecordRepository cardinalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SimpleDateFormat formatDate;
    @Transactional
    @Override
    public CardinalRecord createCardinalRecord(CardinalRecordDTO CardinalRecordDTO) throws ParseException {

        CardinalRecord cardinalRecord = CardinalRecord.builder()
                .Cholesterol(CardinalRecordDTO.getCholesterol())
                .BloodSugar(CardinalRecordDTO.getBloodSugar())
                .HBA1C(CardinalRecordDTO.getHba1c())
                .weekStart(CardinalRecordDTO.getWeekStart())
                .date(CardinalRecordDTO.getDate())
                .timeMeasure(CardinalRecordDTO.getTimeMeasure()).build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        cardinalRecord.setAppUserId(appUser.get());

        String dateStr= formatDate.format(cardinalRecord.getDate());
        Date date = formatDate.parse(dateStr);
        List<CardinalRecord> cardinalRecordListExits = cardinalRecordRepository.findByAppUserId(appUser.get().getId());
        boolean dateExists = cardinalRecordListExits.stream()
                .anyMatch(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(date)
                                &&  record .getTimeMeasure().equals(CardinalRecordDTO.getTimeMeasure());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }

                });
        if (dateExists) {
            throw new AppException(ErrorCode.CARDINAL_TYPE_DAY_EXIST);
        }



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
    public CardinalChartResponseDTO getDataChart() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);

        List<CardinalRecord> cardinalRecordList = cardinalRecordRepository.findByAppUserId(appUser.get().getId());
        cardinalRecordList.sort((recordDateSmaller, recordDateBigger) -> recordDateBigger.getDate().compareTo(recordDateSmaller.getDate()));
        // Lấy 5 bản ghi có ngày gần với ngày hiện tại nhất
        // Tạo một Set để theo dõi các ngày đã được thêm
        Set<Date> uniqueDates = new HashSet<>();
        for (CardinalRecord record : cardinalRecordList) {
            String recordDateStr = formatDate.format(record.getDate());
            Date recordDate = formatDate.parse(recordDateStr);
            if(recordDate.before(date) || recordDate.equals(date)) {
                if(!uniqueDates.contains(recordDate)){
                    uniqueDates.add(recordDate);
                }
            }
            if(uniqueDates.size() == 5){
                break;
            }
        }

        List<HBA1CResponseDTO> hba1CResponseDTOList = new ArrayList<>();
        List<BloodSugarResponseDTO> bloodSugarResponseDTOList = new ArrayList<>();
        List<CholesterolResponseDTO> cholesterolResponseDTOList = new ArrayList<>();
        //Sắp xếp date tăng dần
        Set<Date> sortedDates = new TreeSet<>(uniqueDates);
        for (Date sortedDate : sortedDates) {

            List<CardinalRecord> listByDate = cardinalRecordList.stream()
                    .filter(record -> {
                        String recordDateStr = formatDate.format(record.getDate());
                        try {
                            Date recordDate = formatDate.parse(recordDateStr);
                            String sortedDateStr = formatDate.format(sortedDate);
                            Date parsedSortedDate = formatDate.parse(sortedDateStr);
                            return recordDate.equals(parsedSortedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
            Optional<Float> hba1cByDate = listByDate.stream()
                    .map(CardinalRecord::getHBA1C)
                    .max(Comparator.naturalOrder());
            Optional<Float> cholesterolByDate = listByDate.stream()
                    .map(CardinalRecord::getCholesterol)
                    .max(Comparator.naturalOrder());
            Optional<Float> bloodSugarByDate = listByDate.stream()
                    .map(CardinalRecord::getBloodSugar)
                    .max(Comparator.naturalOrder());
            hba1cByDate.ifPresent(aFloat -> hba1CResponseDTOList.add(
                    HBA1CResponseDTO.builder()
                            .data(aFloat).date(sortedDate).build()
            ));
            cholesterolByDate.ifPresent(aFloat -> cholesterolResponseDTOList.add(
                    CholesterolResponseDTO.builder()
                            .data(aFloat).date(sortedDate).build()
            ));
            bloodSugarByDate.ifPresent(aFloat -> bloodSugarResponseDTOList.add(
                    BloodSugarResponseDTO.builder()
                            .data(aFloat).date(sortedDate).build()
            ));

        }

        CardinalChartResponseDTO planResponseDTO = new CardinalChartResponseDTO();
        planResponseDTO.setHba1cList(hba1CResponseDTOList);
        planResponseDTO.setCholesterolList(cholesterolResponseDTOList);
        planResponseDTO.setBloodSugarList(bloodSugarResponseDTOList);
        Optional<CardinalRecord> cardinalRecord = cardinalRecordList.stream()
                .filter(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        String sortedDateStr = formatDate.format(date);
                        Date parsedSortedDate = formatDate.parse(sortedDateStr);
                        return recordDate.equals(parsedSortedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst();

       planResponseDTO.setHba1cDataToday(cardinalRecord.map(CardinalRecord::getHBA1C).orElse((float) 0));
       planResponseDTO.setCholesterolDataToday(cardinalRecord.map(CardinalRecord::getCholesterol).orElse((float) 0));

        //Thông số chi tiết của blood sugar
        List<DetailBloodSugarResponseDTO> listDetailBloodSugarMorningResponseDTOList = new ArrayList<>();
        List<DetailBloodSugarResponseDTO> listDetailBloodSugarLucnResponseDTOList = new ArrayList<>();
        List<DetailBloodSugarResponseDTO> listDetailBloodSugarDinnerResponseDTOList = new ArrayList<>();
        List<CardinalRecord> listByDate = cardinalRecordList.stream()
                .filter(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        String sortedDateStr = formatDate.format(date);
                        Date parsedSortedDate = formatDate.parse(sortedDateStr);
                        return recordDate.equals(parsedSortedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());

        for (CardinalRecord record : listByDate) {
            if(record.getTimeMeasure().equals(TypeTimeMeasure.AFTER_BREAKFAST)
                    || record.getTimeMeasure().equals(TypeTimeMeasure.BEFORE_BREAKFAST)){
                listDetailBloodSugarMorningResponseDTOList.add(
                        DetailBloodSugarResponseDTO.builder()
                                .data(record.getBloodSugar()).typeTimeMeasure(record.getTimeMeasure().toString()).build()
                );
            }
            if(record.getTimeMeasure().equals(TypeTimeMeasure.BEFORE_LUNCH)
                    || record.getTimeMeasure().equals(TypeTimeMeasure.AFTER_LUNCH)){
                listDetailBloodSugarLucnResponseDTOList.add(
                        DetailBloodSugarResponseDTO.builder()
                                .data(record.getBloodSugar()).typeTimeMeasure(record.getTimeMeasure().toString()).build()
                );
            }

            if(record.getTimeMeasure().equals(TypeTimeMeasure.BEFORE_DINNER)
                    || record.getTimeMeasure().equals(TypeTimeMeasure.AFTER_DINNER)){
                listDetailBloodSugarDinnerResponseDTOList.add(
                        DetailBloodSugarResponseDTO.builder()
                                .data(record.getBloodSugar()).typeTimeMeasure(record.getTimeMeasure().toString()).build()
                );
            }
        }
        planResponseDTO.getDetailDataBloodSugar().put("MORNING",listDetailBloodSugarMorningResponseDTOList);
        planResponseDTO.getDetailDataBloodSugar().put("LUNCH",listDetailBloodSugarLucnResponseDTOList);
        planResponseDTO.getDetailDataBloodSugar().put("DINNER",listDetailBloodSugarDinnerResponseDTOList);
        return planResponseDTO;
    }

    @Override
    public Boolean checkPlanPerDay(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date today = new Date();
        String dateStr= formatDate.format(today);
        Date date = formatDate.parse(dateStr);
        Date weekStartNow = formatDate.parse(weekStart);
        List<CardinalRecord> cardinalRecordList = cardinalRecordRepository.findByAppUserId(appUser.get().getId());
        Optional<CardinalRecord> cardinalRecord = cardinalRecordList.stream()
                .filter(record -> {
                    String recordDateStr = formatDate.format(record.getDate());
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordDate.equals(date)
                                && recordWeekStart.equals(weekStartNow);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        if (cardinalRecord.isEmpty()) {
            throw new AppException(ErrorCode.CARDINAL_DATA_DAY_EMPTY);
        }

        return true;

    }

    @Transactional
    @Override
    public void updateCardinalRecord(Integer id, CardinalRecordDTO CardinalRecordDTO) {
        CardinalRecord cardinalRecord = getCardinalRecordById(id);
        cardinalRecord.setCholesterol(CardinalRecordDTO.getCholesterol());
        cardinalRecord.setBloodSugar(CardinalRecordDTO.getBloodSugar());
        cardinalRecord.setHBA1C(CardinalRecordDTO.getHba1c());
        cardinalRecord.setWeekStart(CardinalRecordDTO.getWeekStart());
        cardinalRecord.setDate(CardinalRecordDTO.getDate());
        cardinalRecord.setTimeMeasure(CardinalRecordDTO.getTimeMeasure());
        cardinalRecordRepository.save(cardinalRecord);
    }

    @Override
    public CardinalRecord deleteCardinalRecord(Integer id) {
        CardinalRecord cardinalRecord = getCardinalRecordById(id);
        cardinalRecordRepository.deleteById(id);
        return cardinalRecord;
    }


}