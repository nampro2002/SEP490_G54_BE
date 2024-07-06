package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.*;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.*;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeeklyReviewServiceImpl implements WeeklyReviewService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CardinalRecordRepository cardinalRecordRepository;
    @Autowired
    private BloodPressureRecordRepository bloodPressureRecordRepository;
    @Autowired
    private WeightRecordRepository weightRecordRepository;
    @Autowired
    private MentalRecordRepository mentalRecordRepository;
    @Autowired
    private ActivityRecordRepository activityRecordRepository;
    @Autowired
    private DietRecordRepository dietRecordRepository;
    @Autowired
    private MedicineRecordRepository medicineRecordRepository;
    @Autowired
    private StepRecordRepository stepRecordRepository;
    @Autowired
    private  SimpleDateFormat formatDate;
    @Autowired
    private WeekReviewRepository weekReviewRepository;

    @Override
    public WeekReview getWeek(Integer id) throws ParseException {
        WeeklyReviewResponseDTO weeklyReviewResponseDTO = new WeeklyReviewResponseDTO();

        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //trả về ngày sớm nhất của user
        Date smallestWeekStart = findSmallestWeekStart(appUser.get());
        //week start for filter
        String weekStartStr=formatDate.format(smallestWeekStart);
        Date weekStartFilter = formatDate.parse(weekStartStr);
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(appUser.get().getId());
        Optional<WeekReview> weekReviewExist = weekReviews.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartFilter);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        if (weekReviewExist.isEmpty()) {
            throw new AppException(ErrorCode.WEEK_REVIEW_NOT_EXIST);
        }
        return weekReviewExist.get();
    }

    @Override
    public List<Date> getListWeekStart(Integer id) throws ParseException {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //trả về ngày sớm nhất của user
        Date smallestWeekStart = findSmallestWeekStart(appUser.get());
        List<Date> weekStartList = new ArrayList<>();

        weekStartList.add(smallestWeekStart);

        //Lấy ra ngày hiện tại và gán ngày sớm nhất cho datePlus7
        Date today = new Date();
        Date datePlus7 = smallestWeekStart;

        //Vòng lặp cho đến ngày hiện tại
        boolean loopStatus = true;
        for (; loopStatus;) {
            datePlus7 = calculateDate(datePlus7,7);
                // Kiểm tra xem datePlus7 nhỏ hơn ngày hôm nay
                if (datePlus7.before(today) ) {
                        weekStartList.add(datePlus7);
                }else{ // dateplus mà quá lớn hơn hiên tại thì dừng
                    loopStatus = false;
                }
        }
        return weekStartList;
    }

    public Date calculateDate(Date sourceDate , int plus) throws ParseException {
        // Tạo một đối tượng Calendar và set ngày tháng từ đối tượng Date đầu vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        // Cộng thêm một ngày
        calendar.add(Calendar.DAY_OF_MONTH, plus);
        // Trả về Date sau khi cộng thêm ngày
        return calendar.getTime();
    }


    @Override
    public WeekReview getWebDataReviewForWeek(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //week start for filter
        Date weekStartFilter = formatDate.parse(weekStart);
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(appUser.get().getId());
        Optional<WeekReview> weekReviewExist = weekReviews.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartFilter);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        if (weekReviewExist.isEmpty()) {
            throw new AppException(ErrorCode.WEEK_REVIEW_NOT_EXIST);
        }
        return weekReviewExist.get();
    }

    @Override
    public WeeklyReviewResponseDTO getMobileDataReviewForWeek(String weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //week start for filter
        Date weekStartFilter = formatDate.parse(weekStart);
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(appUser.get().getId());
        Optional<WeekReview> weekReviewExist = weekReviews.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartFilter);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        if (weekReviewExist.isEmpty()) {
            throw new AppException(ErrorCode.WEEK_REVIEW_NOT_EXIST);
        }
        WeeklyReviewResponseDTO responseDTO = new WeeklyReviewResponseDTO().builder()
                .weekStart(weekReviewExist.get().getWeekStart())
                .hba1cTotalRecord(weekReviewExist.get().getHba1cTotalRecord())
                .hba1cSafeRecord(weekReviewExist.get().getHba1cSafeRecord())
                .cholesterolTotalRecord(weekReviewExist.get().getCholesterolTotalRecord())
                .cholesterolSafeRecord(weekReviewExist.get().getCholesterolSafeRecord())
                .bloodSugarTotalRecord(weekReviewExist.get().getBloodSugarTotalRecord())
                .bloodSugarSafeRecord(weekReviewExist.get().getBloodSugarSafeRecord())
                .totalBloodPressureRecord(weekReviewExist.get().getTotalBloodPressureRecord())
                .safeBloodPressureRecord(weekReviewExist.get().getSafeBloodPressureRecord())
                .averageWeightRecordPerWeek(weekReviewExist.get().getAverageWeightRecordPerWeek())
                .averageMentalRecordPerWeek(weekReviewExist.get().getAverageMentalRecordPerWeek())
                .heavyActivity(weekReviewExist.get().getHeavyActivity())
                .mediumActivity(weekReviewExist.get().getMediumActivity())
                .lightActivity(weekReviewExist.get().getLightActivity())
                .averageDietRecordPerWeek(weekReviewExist.get().getAverageDietRecordPerWeek())
                .averageStepRecordPerWeek(weekReviewExist.get().getAverageStepRecordPerWeek())
                .totalPoint(weekReviewExist.get().getTotalPoint())
                .build();

       //Tìm danh sách thuốc theo tuần
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(appUser.get().getId());
        List<MedicineRecord> medicineRecordListByWeekStart = medicineRecordList.stream()
                .filter(item -> {
                    String itemDateStr = formatDate.format(item.getWeekStart());
                    try {
                        Date itemDate = formatDate.parse(itemDateStr);
                        return itemDate.equals(weekStartFilter);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).toList();
        //Lấy ra date unique
        Set<Date> uniqueDates = new HashSet<>();
        for (MedicineRecord record : medicineRecordListByWeekStart) {
            String itemDateStr = formatDate.format(record.getDate());
            Date itemDate = formatDate.parse(itemDateStr);
            if (!uniqueDates.contains(itemDate)) {
                uniqueDates.add(itemDate);
            }
        }

        //Sắp xếp date tăng dần
        Set<Date> sortedDates = new TreeSet<>(uniqueDates);
        //Đếm từng ngày xem có uống đủ thuốc không
        int countDone = 0;
        for (Date date : sortedDates) {

            boolean dataNullExists = medicineRecordListByWeekStart.stream()
                        .anyMatch(item -> {
                            String itemDateStr = formatDate.format(item.getDate());
                            try {
                                Date itemDate = formatDate.parse(itemDateStr);
                                return itemDate.equals(date)
                                        && (item.getStatus() == null || item.getStatus() == false);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        });
                // ngày hôm đấy uống đủ
                if (dataNullExists == false) {
                    countDone++;
                }
        }
        responseDTO.setMedicineDateDone(countDone);
        responseDTO.setMedicineDateTotal(uniqueDates.size());

        return responseDTO;
    }

    @Override
    public WeeklyMoblieChartResponseDTO getMobileChartReviewForWeek() throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(appUser.get().getId());
        //Sắp xếp giảm dần theo WeekStart
        weekReviews.sort(new Comparator<WeekReview>() {
            @Override
            public int compare(WeekReview recordDateSmaller, WeekReview recordDateBigger) {
                return recordDateBigger.getWeekStart().compareTo(recordDateSmaller.getWeekStart());
            }
        });
        //Lấy ra 5 gần nhất  weekStart
        List<WeekReview> fiveNearlyDates = new ArrayList<>();
        for (WeekReview record : weekReviews) {
                if (!fiveNearlyDates.contains(fiveNearlyDates)) {
                    fiveNearlyDates.add(record);
                }
                if(fiveNearlyDates.size() > 5){
                    break;
                }
        }
        //Sắp xếp giảm dần theo date
        fiveNearlyDates.sort(new Comparator<WeekReview>() {
            @Override
            public int compare(WeekReview recordDateSmaller, WeekReview recordDateBigger) {
                return recordDateBigger.getWeekStart().compareTo(recordDateSmaller.getWeekStart());
            }
        });
        WeeklyMoblieChartResponseDTO response = new WeeklyMoblieChartResponseDTO();
        for (WeekReview weekReview : fiveNearlyDates) {
            response.getWeekStart().add(weekReview.getWeekStart());
            response.getPercentage().add(weekReview.getTotalPoint());
        }
    return response;
    }
    public double calculateTotalPointOfWeek(Date weekStart) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //Lấy danh sách theo User
        List<ActivityRecord> activityRecordList = activityRecordRepository.findRecordByIdUser(appUser.get().getId());
        List<BloodPressureRecord> bloodPressureRecordList = bloodPressureRecordRepository.findAllByUserId(appUser.get().getId());
        List<CardinalRecord> cardinalRecordList = cardinalRecordRepository.findByAppUserId(appUser.get().getId());
        List<DietRecord> dietRecordList = dietRecordRepository.findByAppUser(appUser.get().getId());
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByAppUser(appUser.get().getId());
        List<MentalRecord> mentalRecordList = mentalRecordRepository.findByAppUserId(appUser.get().getId());
        List<StepRecord> stepRecordExist = stepRecordRepository.findByAppUserId(appUser.get().getId());
        List<WeightRecord> weightRecordList = weightRecordRepository.findAppUser(appUser.get().getId());

        //Lấy theo weekStart
        activityRecordList = activityRecordList.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        bloodPressureRecordList = bloodPressureRecordList.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        cardinalRecordList = cardinalRecordList.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        dietRecordList = dietRecordList.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        medicineRecordList = medicineRecordList.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        mentalRecordList = mentalRecordList.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        stepRecordExist = stepRecordExist.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        weightRecordList = weightRecordList.stream().filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return weekStart.equals(recordWeekStart);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
        //tính điểm thành phần
        double mentalPercentage = (double) mentalRecordList.stream().filter(record -> record.getStatus() != null).count() / 21 * 100;
        double activityPercentage = (double) activityRecordList.stream().filter(record -> record.getActualType() != null).count() / 7 * 100;
        double dietPercentage = (double) dietRecordList.stream().filter(record -> record.getActualValue() > 0).count() / 7 * 100;
        double medicinePercentage = (double) medicineRecordList.stream().filter(record -> record.getStatus() != null).count()/
                        mentalRecordList.size() * 100;
        double stepPercentage = (double) stepRecordExist.stream().filter(record -> record.getActualValue() > 0).count() / 7 * 100;
        double bloodPressurePercentage = (double) bloodPressureRecordList.size() / 7 * 100;
        double weightPercentage = (double) weightRecordList.size() / 7 * 100;

        // Unique Date Cardinal
        Set<Date> uniqueDates = new HashSet<>();
        for (CardinalRecord record : cardinalRecordList) {
            String recordDateStr = formatDate.format(record.getDate());
            Date recordDate = formatDate.parse(recordDateStr);
                boolean dataNullExists = cardinalRecordList.stream()
                        .anyMatch(item -> {
                            String itemDateStr = formatDate.format(item.getDate());
                            try {
                                Date itemDate = formatDate.parse(itemDateStr);
                                return itemDate.equals(recordDate)
                                  ;
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        });
                // ko có value nào bị null
//                if (dataNullExists == false) {
                    if (!uniqueDates.contains(recordDate)) {
                        uniqueDates.add(recordDate);
                    }
//                }
        }

        //Count not null theo date
        int countHba1cNotNull = 0;
        int countCholesterolNotNull = 0;
        int countBloodSugarNotNull = 0;
        Set<Date> sortedDate = new TreeSet<>(uniqueDates);
        for (Date date : sortedDate) {
            boolean hba1cNotNull = cardinalRecordList.stream()
                    .anyMatch(item -> {
                        String itemDateStr = formatDate.format(item.getDate());
                        try {
                            Date itemDate = formatDate.parse(itemDateStr);
                            return itemDate.equals(date)
                                    && item.getHBA1C() != null;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
            if(hba1cNotNull == true){
                countHba1cNotNull++;
            }
            boolean cholesterolNotNull = cardinalRecordList.stream()
                    .anyMatch(item -> {
                        String itemDateStr = formatDate.format(item.getDate());
                        try {
                            Date itemDate = formatDate.parse(itemDateStr);
                            return itemDate.equals(date)
                                    && item.getCholesterol() != null
                                    ;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
            if(cholesterolNotNull == true){
                countCholesterolNotNull++;
            }
            boolean bloodSugarNotNull = cardinalRecordList.stream()
                    .anyMatch(item -> {
                        String itemDateStr = formatDate.format(item.getDate());
                        try {
                            Date itemDate = formatDate.parse(itemDateStr);
                            return itemDate.equals(date)
                                    && item.getCholesterol() != null
                                    ;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
            if(bloodSugarNotNull == true){
                countBloodSugarNotNull++;
            }
        }
        //tính điểm thành phần
        double hba1ctPercentage =  (double) countHba1cNotNull/7*100;
        double cholesterolPercentage =  (double) countCholesterolNotNull/7*100;
        double bloodSugarPercentage =  (double) countBloodSugarNotNull/7*100;
//        System.out.println(mentalPercentage);
//        System.out.println(activityPercentage);
//        System.out.println(dietPercentage);
//        System.out.println(medicinePercentage);
//        System.out.println(stepPercentage);
//        System.out.println(bloodPressurePercentage);
//        System.out.println(weightPercentage);
//        System.out.println(hba1ctPercentage);
//        System.out.println(cholesterolPercentage);
//        System.out.println(bloodSugarPercentage);
        //Điểm tổng
        double pointTotal=
                        (calculatePointOfWeek(mentalPercentage)+
                        calculatePointOfWeek(activityPercentage)+
                        calculatePointOfWeek(dietPercentage)+
                        calculatePointOfWeek(medicinePercentage)+
                        calculatePointOfWeek(stepPercentage)+
                        calculatePointOfWeek(bloodPressurePercentage)+
                        calculatePointOfWeek(weightPercentage)+
                        calculatePointOfWeek(hba1ctPercentage)+
                        calculatePointOfWeek(cholesterolPercentage)+
                        calculatePointOfWeek(bloodSugarPercentage))/30*100;
        return pointTotal;
    }

    public double calculatePointOfWeek(double percentage){
        if(percentage >= 90){
            return 3;
        }
        else if(percentage >= 50){
            return 2;
        }
        else if(percentage >= 0){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public void saveDataReviewForWeek(String weekStart) throws ParseException {
        WeekReview weekReview = new WeekReview();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if(appUser.isEmpty()){
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //week start for filter
        Date weekStartFilter = formatDate.parse(weekStart);
        List<WeekReview> weekReviews = weekReviewRepository.findByAppUserId(appUser.get().getId());
        Optional<WeekReview> weekReviewExist = weekReviews.stream()
                .filter(record -> {
                    String recordWeekStartStr = formatDate.format(record.getWeekStart());
                    try {
                        Date recordWeekStart = formatDate.parse(recordWeekStartStr);
                        return recordWeekStart.equals(weekStartFilter);
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .findFirst();
        if (weekReviewExist.isPresent()) {
            throw new AppException(ErrorCode.WEEK_REVIEW_EXIST);
        }

        weekReview.setAppUserId(appUser.get());
        weekReview.setWeekStart(weekStartFilter);
        //Average cardinal per week
        CardinalPerWeekResponseDTO  cardinalPerWeekResponseDTO = getAverageCardinalPerWeek(appUser.get(), weekStartFilter);
        weekReview.setHba1cTotalRecord(cardinalPerWeekResponseDTO.getHba1cTotalRecord());
        weekReview.setHba1cSafeRecord(cardinalPerWeekResponseDTO.getHba1cSafeRecord());
        weekReview.setCholesterolTotalRecord(cardinalPerWeekResponseDTO.getCholesterolTotalRecord());
        weekReview.setCholesterolSafeRecord(cardinalPerWeekResponseDTO.getCholesterolSafeRecord());
        weekReview.setBloodSugarTotalRecord(cardinalPerWeekResponseDTO.getBloodSugarTotalRecord());
        weekReview.setBloodSugarSafeRecord(cardinalPerWeekResponseDTO.getBloodSugarSafeRecord());

        //Average bloodPressure per week
        BloodPressurePerWeekResponseDTO bloodPressurePerWeekResponseDTO =getAverageBloodPressurePerWeek(appUser.get(), weekStartFilter);
        weekReview.setTotalBloodPressureRecord(bloodPressurePerWeekResponseDTO.getTotalRecord());
        weekReview.setSafeBloodPressureRecord(bloodPressurePerWeekResponseDTO.getSafeRecord());
        //Average Weight per week
        weekReview.setAverageWeightRecordPerWeek(getAverageWeightPerWeek(appUser.get(), weekStartFilter));
        //Average Mental per week
        weekReview.setAverageMentalRecordPerWeek(getMentalPerWeek(appUser.get(), weekStartFilter));
        //Review Activity per week
        ActivityPerWeekResponseDTO activityPerWeekResponseDTO =getActivityPerWeek(appUser.get(), weekStartFilter);
        weekReview.setHeavyActivity(activityPerWeekResponseDTO.getHeavyActivity());
        weekReview.setMediumActivity(activityPerWeekResponseDTO.getMediumActivity());
        weekReview.setLightActivity(activityPerWeekResponseDTO.getLightActivity());
        //Average diet per week
        weekReview.setAverageDietRecordPerWeek(getAverageDietPerWeek(appUser.get(), weekStartFilter));
        //get Done and Total medicine per week
        MedicinePerWeekResponseDTO medicinePerWeekResponseDTO = getMedicinePerWeek(appUser.get(),weekStartFilter);
        weekReview.setMedicineRecordDone(medicinePerWeekResponseDTO.getMedicineRecordDone());
        weekReview.setMedicineRecordTotal(medicinePerWeekResponseDTO.getMedicineRecordTotal());
        //Average step per week
        weekReview.setAverageStepRecordPerWeek(getAverageStepPerWeek(appUser.get(),weekStartFilter));
        //Total point per week
        weekReview.setTotalPoint((int) calculateTotalPointOfWeek(weekStartFilter));
        weekReviewRepository.save(weekReview);
    }



    private CardinalPerWeekResponseDTO getAverageCardinalPerWeek(AppUser appUser, Date weekStart) {
        CardinalPerWeekResponseDTO responseDTO = new CardinalPerWeekResponseDTO();
        List<CardinalRecord> cardinalRecordList = cardinalRecordRepository.findAll().stream()
                .filter(
                        record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                                && record.getAppUserId() == appUser)
                .collect(Collectors.toList());
        int hba1c = 0;
        int cholesterol = 0;
        int bloodSugar = 0;

        int hba1cTotal = 0;
        int cholesterolTotal = 0;
        int bloodSugarTotal = 0;
        for (CardinalRecord record : cardinalRecordList) {
            hba1c += (record.getHBA1C() != null && record.getHBA1C() <= 7.5 ) ? 1 : 0;
            cholesterol += (record.getCholesterol() != null && record.getCholesterol() <= 200 ) ? 1 : 0;
            bloodSugar += (record.getBloodSugar() != null && record.getBloodSugar() <= 99 ) ? 1 : 0;

            hba1cTotal += (record.getHBA1C() != null) ? 1 : 0;
            cholesterolTotal += (record.getCholesterol() != null ) ? 1 : 0;
            bloodSugarTotal += (record.getBloodSugar() != null) ? 1 : 0;
        }
        responseDTO.setCholesterolSafeRecord(cholesterol);
        responseDTO.setCholesterolTotalRecord(cholesterolTotal);

        responseDTO.setBloodSugarSafeRecord(bloodSugar);
        responseDTO.setBloodSugarTotalRecord(bloodSugarTotal);

        responseDTO.setHba1cSafeRecord(hba1c);
        responseDTO.setHba1cTotalRecord(hba1cTotal);
        return responseDTO;
    }

    private BloodPressurePerWeekResponseDTO getAverageBloodPressurePerWeek(AppUser appUser, Date weekStart) {
        BloodPressurePerWeekResponseDTO responseDTO = new BloodPressurePerWeekResponseDTO();
        List<BloodPressureRecord> bloodPressureRecordList = bloodPressureRecordRepository.findAll().stream()
                .filter(
                        record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                                && record.getAppUserId() == appUser)
                .collect(Collectors.toList());
        int count = 0;
        for (BloodPressureRecord record : bloodPressureRecordList) {
            if(checkBloodPressure(record.getSystole(),record.getDiastole())){
                count++;
            }
        }
        responseDTO.setSafeRecord(count);
        responseDTO.setTotalRecord(bloodPressureRecordList.size());
        return responseDTO;
    }

    private boolean checkBloodPressure(float systole , float diastole ){
        return (systole <= 120 && diastole <= 80) == true ? true :false;
    }

    private  int getAverageWeightPerWeek(AppUser appUser, Date weekStart) {
        List<WeightRecord> weightRecordList = weightRecordRepository.findAll().stream()
                .filter(
                        record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                                && record.getAppUserId() == appUser)
                .collect(Collectors.toList());
        double sum = 0;
        for (WeightRecord record : weightRecordList) {
            sum += record.getWeight();
        }
        int result = (int) (sum / 7);
        return result;
    }

    private  int getMentalPerWeek(AppUser appUser, Date weekStart) {
        List<MentalRecord> mentalRecordList = mentalRecordRepository.findAll().stream()
                .filter(
                        record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                                && record.getAppUserId() == appUser && record.getStatus() != null)
                .collect(Collectors.toList());
        double sum = 0;
        for (MentalRecord record : mentalRecordList) {
            if(record.getStatus() == true ){
                sum += 1;
            }
        }
        int result = (int) (sum / 7);
        return result;
    }

    private ActivityPerWeekResponseDTO getActivityPerWeek(AppUser appUser, Date weekStart) {
        ActivityPerWeekResponseDTO responseDTO = new ActivityPerWeekResponseDTO();
        List<ActivityRecord> activityRecordList = activityRecordRepository.findAll().stream()
                .filter(
                        record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                                && record.getAppUserId() == appUser)
                .collect(Collectors.toList());
        int heavy = 0;
        int medium = 0;
        int light = 0;
        for (ActivityRecord record : activityRecordList) {
            heavy += record.getActualType() == TypeActivity.HEAVY ? record.getActualDuration() : 0;
            medium += record.getActualType() == TypeActivity.MEDIUM ? record.getActualDuration(): 0;
            light += record.getActualType() == TypeActivity.LIGHT ? record.getActualDuration() : 0;
        }
        responseDTO.setHeavyActivity(heavy);
        responseDTO.setMediumActivity(medium);
        responseDTO.setLightActivity(light);
        return responseDTO;
    }

    private  int getAverageDietPerWeek(AppUser appUser, Date weekStart) {
        List<DietRecord> dietRecordList = dietRecordRepository.findAll().stream()
                .filter(
                        record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                                && record.getAppUserId() == appUser)
                .collect(Collectors.toList());
        double sum = 0;
        for (DietRecord record : dietRecordList) {
            sum += record.getActualValue();
        }
        int result = (int) (sum / 7);
        return result;
    }

    private MedicinePerWeekResponseDTO getMedicinePerWeek(AppUser appUser, Date weekStart) {
        MedicinePerWeekResponseDTO responseDTO = new MedicinePerWeekResponseDTO();
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findAll().stream()
                .filter(
                        record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                                && record.getAppUserId() == appUser && record.getStatus() != null)
                .collect(Collectors.toList());
        int count = 0;
        for (MedicineRecord record : medicineRecordList) {
            if(record.getStatus() == true){
                count++;
            }
        }
        responseDTO.setMedicineRecordDone(count);
        responseDTO.setMedicineRecordTotal(medicineRecordList.size());
        return responseDTO;
    }

    private  int getAverageStepPerWeek(AppUser appUser, Date weekStart) {
        List<StepRecord> stepRecordList = stepRecordRepository.findAll().stream()
                .filter(
                 record -> formatDate.format(record.getWeekStart()).equals(formatDate.format(weekStart))
                         && record.getAppUserId() == appUser)
                .collect(Collectors.toList());
        double sum = 0;
        for (StepRecord record : stepRecordList) {
            sum += record.getActualValue();
        }
        int result = (int) (sum / 7);
        return result;
    }

    private List<Date> getArraySmallestWeekStartByUser(Integer appUserId) {
        List<Date> smallestDateList = new ArrayList<>();

        stepRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId().getId() == appUserId)
                .map(StepRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

        cardinalRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId().getId() == appUserId)
                .map(CardinalRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

        bloodPressureRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId().getId() == appUserId)
                .map(BloodPressureRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       weightRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId().getId() == appUserId)
                .map(WeightRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       mentalRecordRepository.findAll().stream()
        .filter(record -> record.getAppUserId().getId() == appUserId)
        .map(MentalRecord::getWeekStart)
        .min(Comparator.naturalOrder())
        .ifPresent(smallestDateList::add);

       dietRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId().getId() == appUserId)
                .map(DietRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       medicineRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId().getId() == appUserId)
                .map(MedicineRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       activityRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId().getId() == appUserId)
                .map(ActivityRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);
       return smallestDateList;
    }

    public Date findSmallestWeekStart(AppUser appUser) {
        List<Date> smallestDateList = getArraySmallestWeekStartByUser(appUser.getId());
        if (smallestDateList.isEmpty()) {
            throw new AppException(ErrorCode.USER_WEEK_START_NOT_EXIST);
        }
        return smallestDateList.stream()
                .min(Date::compareTo)
                .orElse(null);
    }
}