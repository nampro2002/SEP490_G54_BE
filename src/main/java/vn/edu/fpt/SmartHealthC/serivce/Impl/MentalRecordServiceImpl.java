package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalDTO.MentalResponseChartDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.MentalRecordService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MentalRecordServiceImpl implements MentalRecordService {

    @Autowired
    private MentalRecordRepository mentalRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MentalRuleRepository mentalRuleRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SimpleDateFormat formatDate;

    public Date calculateDate(Date date, int plus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, plus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }

    @Transactional
    @Override
    public void createMentalRecord(MentalRecordCreateDTO mentalRecordDTO) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        String weekStartStr = formatDate.format(mentalRecordDTO.getWeekStart());
        Date weekStart = formatDate.parse(weekStartStr);
        List<MentalRecord> mentalRecordExist = mentalRecordRepository.findByAppUserId(appUser.get().getId());
        boolean dateExists = mentalRecordExist.stream()
                .anyMatch(record -> {
                    String recordDateStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordDate = formatDate.parse(recordDateStr);
                        return recordDate.equals(weekStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                });
        if (dateExists) {
            throw new AppException(ErrorCode.MENTAL_PLAN_EXIST);
        }

        int count = 0;
        Date dateCalculate;
        while (true) {
            if (count >= 7) {
                break;
            }
            dateCalculate = calculateDate(mentalRecordDTO.getWeekStart(), count);
            for (Integer id : mentalRecordDTO.getMentalRuleId()) {
                MentalRecord mentalRecord = MentalRecord.builder()
                        .weekStart(DateUtils.normalizeDate(formatDate, weekStartStr))
                        .date(dateCalculate)
                        .build();
                mentalRecord.setAppUserId(appUser.get());

                Optional<MentalRule> mentalRule = mentalRuleRepository.findById(id);
                if (mentalRule.isEmpty()) {
                    throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
                }
                mentalRecord.setMentalRule(mentalRule.get());
                mentalRecordRepository.save(mentalRecord);
            }
            count++;
        }

    }

    @Override
    public MentalRecord getMentalRecordEntityById(Integer id) {
        Optional<MentalRecord> mentalRecord = mentalRecordRepository.findById(id);
        if (mentalRecord.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_NOT_FOUND);
        }
        return mentalRecord.get();
    }

    @Override
    public MentalRecordResponseDTO getMentalRecordById(Integer id) {
        Optional<MentalRecord> mentalRecord = mentalRecordRepository.findById(id);
        if (mentalRecord.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_NOT_FOUND);
        }
        return MentalRecordResponseDTO.builder()
                .appUserId(mentalRecord.get().getAppUserId().getId())
                .status(mentalRecord.get().getStatus())
                .weekStart(mentalRecord.get().getWeekStart())
                .date(mentalRecord.get().getDate())
                .mentalRuleId(mentalRecord.get().getMentalRule().getId())
                .build();
    }


    @Override
    public List<MentalRecordListResDTO> getAllMentalRecords(Integer userId) {
        List<Date> mentalDate = mentalRecordRepository.findDistinctDate(userId);
        List<MentalRecordListResDTO> listResponseDTOList = new ArrayList<>();
        for (Date date : mentalDate) {
            listResponseDTOList.add(MentalRecordListResDTO.builder()
                    .date(date)
                    .build());
        }
        for (MentalRecordListResDTO record : listResponseDTOList) {
            List<MentalRecord> mentalRecords = mentalRecordRepository.findByAppUserIdAndDate(userId, record.getDate());
            record.setMentalRuleTitle(new ArrayList<>());
            int count = 0;
            for (MentalRecord mentalRecord : mentalRecords) {
                if (mentalRecord.getStatus() == null) {
                    continue;
                }
                if (mentalRecord.getStatus()) {
                    record.getMentalRuleTitle().add(mentalRecord.getMentalRule().getTitleEn());
                    count++;
                }
            }
            record.setStatus(count + "/" + mentalRecords.size());
        }
        return listResponseDTOList;
    }

    @Transactional
    @Override
    public void updateMentalRecord(MentalRecordUpdateDTO mentalRecordDTO) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        String dateStr = formatDate.format(mentalRecordDTO.getDate());
        Date date = formatDate.parse(dateStr);
        boolean ruleExists = false;
        List<MentalRecord> planExist = mentalRecordRepository.findByAppUserId(appUser.get().getId());
        for (Integer rule : mentalRecordDTO.getMentalRuleId()) {
            ruleExists = planExist.stream()
                    .anyMatch(record -> record.getMentalRule().getId().equals(rule));
        }
        if (!ruleExists) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }

        //Lấy ra toàn bộ mental của ngày hôm đó
        Set<Integer> mentalTypeExist = new HashSet<>();
        for (MentalRecord mentalRecord : planExist) {
            String recordDateStr = formatDate.format(mentalRecord.getDate());
            Date recordDate = formatDate.parse(recordDateStr);
            if (recordDate.equals(date)) {
                if (!mentalTypeExist.contains(mentalRecord.getMentalRule().getId())) {
                    mentalTypeExist.add(mentalRecord.getMentalRule().getId());
                }
            }
        }

        for (Integer rule : mentalTypeExist) {
            Optional<MentalRecord> mentalRecord = planExist.stream()
                    .filter(record -> {
                        String recordDateStr = formatDate.format(record.getDate());
                        try {
                            Date recordDate = formatDate.parse(recordDateStr);
                            return recordDate.equals(date)
                                    && record.getMentalRule().getId().equals(rule);
                        } catch (ParseException e) {
                            return false;
                        }
                    })
                    .findFirst();
            if (mentalRecord.isEmpty()) {
                throw new AppException(ErrorCode.MENTAL_RULE_IN_PLAN_NOT_FOUND);
            }

            MentalRecord mentalRecordUpdate = getMentalRecordEntityById(mentalRecord.get().getId());
            mentalRecordUpdate.setDate(mentalRecordDTO.getDate());
            if (mentalRecordDTO.getMentalRuleId().contains(rule)) {
                mentalRecordUpdate.setStatus(mentalRecordDTO.getStatus());
            } else {
                mentalRecordUpdate.setStatus(false);
            }
            mentalRecordRepository.save(mentalRecordUpdate);
        }
    }

    @Override
    public MentalRecordResponseDTO deleteMentalRecord(Integer id) {
        MentalRecord mentalRecord = getMentalRecordEntityById(id);
        mentalRecordRepository.deleteById(id);
        return MentalRecordResponseDTO.builder()
                .appUserId(mentalRecord.getAppUserId().getId())
                .status(mentalRecord.getStatus())
                .weekStart(mentalRecord.getWeekStart())
                .date(mentalRecord.getDate())
                .mentalRuleId(mentalRecord.getMentalRule().getId())
                .build();
    }

    @Override
    public MentalResponseChartDTO getDataChart() throws ParseException {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Date date = DateUtils.getToday(formatDate);

        List<MentalRecord> mentalRecordList = mentalRecordRepository.findByAppUserId(appUser.getId());
        //Sắp xếp giảm dần theo date
        mentalRecordList.sort(new Comparator<MentalRecord>() {
            @Override
            public int compare(MentalRecord recordDateSmaller, MentalRecord recordDateBigger) {
                return recordDateBigger.getDate().compareTo(recordDateSmaller.getDate());
            }
        });
        //Lấy date unique
        Set<Date> uniqueDates = new HashSet<>();
        for (MentalRecord record : mentalRecordList) {
            String recordDateStr = formatDate.format(record.getDate());
            Date recordDate = formatDate.parse(recordDateStr);
            if (recordDate.before(date) || recordDate.equals(date)) {
                if (!uniqueDates.contains(recordDate)) {
                    List<MentalRecord> mentalRecords = mentalRecordList.stream()
                            .filter(record1 -> {
                                try {
                                    String sortedDateStr = formatDate.format(record1.getDate());
                                    Date parsedSortedDate = formatDate.parse(sortedDateStr);
                                    return recordDate.equals(parsedSortedDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return false;
                                }
                            })
                            .toList();
                    boolean mentalNotInput = mentalRecords.stream()
                            .anyMatch(record2 -> record2.getStatus() == null);
                    if (!mentalNotInput) {
                        uniqueDates.add(recordDate);
                    }
                }
            }
            if (uniqueDates.size() == 6) {
                break;
            }
        }
        //Sắp xếp date tăng dần
        Set<Date> sortedDates = new TreeSet<>(uniqueDates);

        //Đếm status bằng true của tưừng date
        MentalResponseChartDTO mentalResponseChartDTO = new MentalResponseChartDTO();
        for (Date sortedDate : sortedDates) {
            long point = mentalRecordList.stream()
                    .filter(record -> {
                        String recordDateStr = formatDate.format(record.getDate());
                        try {
                            Date recordDate = formatDate.parse(recordDateStr);
                            String sortedDateStr = formatDate.format(sortedDate);
                            Date parsedSortedDate = formatDate.parse(sortedDateStr);
                            return recordDate.equals(parsedSortedDate)
                                    && record.getStatus() == true;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    })
                    .count();
            mentalResponseChartDTO.getMentalResponseList().add(
                    new MentalResponse().builder().point((int) point).date(sortedDate).build()
            );
        }
        // Tính trung bình và lấy 2 chữ số sau dấu phẩy
        mentalResponseChartDTO.setAvgPoint(
                BigDecimal.valueOf(
                        mentalResponseChartDTO.getMentalResponseList().stream()
                                .mapToDouble(MentalResponse::getPoint)
                                .average()
                                .orElse(0.0) // Giả sử nếu không có giá trị nào thì trả về 0.0
                ).setScale(1, RoundingMode.HALF_UP).doubleValue()
        );
        return mentalResponseChartDTO;
    }

    @Override
    public List<MentalRule> getListMentalPerWeek(String weekStart, TypeLanguage language) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date weekStartFind = formatDate.parse(weekStart);

        List<MentalRecord> mentalRecordList = mentalRecordRepository.findByAppUserId(appUser.get().getId());
        Set<MentalRule> uniqueRule = new TreeSet<>(Comparator.comparingInt(MentalRule::getId));
        for (MentalRecord record : mentalRecordList) {
            String recordDateStr = formatDate.format(record.getWeekStart());
            Date recordDate = formatDate.parse(recordDateStr);
            if (recordDate.equals(weekStartFind)) {
                if (!uniqueRule.contains(record.getMentalRule())) {
                    record.getMentalRule().setTitle(language == TypeLanguage.EN ? record.getMentalRule().getTitleEn() : record.getMentalRule().getTitle() );
                    uniqueRule.add( record.getMentalRule());
                }
            }
        }

        return uniqueRule.stream().toList();
    }

    @Override
    public Boolean checkPlanPerDay(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Date today = new Date();
        String dateStr = formatDate.format(today);
        Date date = formatDate.parse(dateStr);
        Date weekStartNow = formatDate.parse(weekStart);
        List<MentalRecord> mentalRecordList = mentalRecordRepository.findByAppUserId(appUser.get().getId());
        List<MentalRecord> mentalRecords = mentalRecordList.stream()
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
                .toList();
        //Không có plan
        if (mentalRecords.isEmpty()) {
//            throw new AppException(ErrorCode.MENTAL_PLAN_NOT_FOUND);
            return false;
        }
        boolean mentalNotInput = mentalRecords.stream()
                .anyMatch(record -> record.getStatus() == null);
        if (mentalNotInput) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkPlanExist(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        Date weekStartNow = formatDate.parse(weekStart);
        List<MentalRecord> mentalRecordList = mentalRecordRepository.findByAppUserId(appUser.get().getId());
        List<MentalRecord> mentalRecords = mentalRecordList.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartNow);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        //Không có plan
        if (mentalRecords.isEmpty()) {
            return false;
        }
        return true;
    }
}