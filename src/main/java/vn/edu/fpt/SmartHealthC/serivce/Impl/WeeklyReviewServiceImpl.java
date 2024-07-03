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
    public WeeklyReviewResponseDTO getWeek(Integer id) throws ParseException {
        WeeklyReviewResponseDTO weeklyReviewResponseDTO = new WeeklyReviewResponseDTO();

        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //trả về ngày sớm nhất của user
        Date smallestWeekStart = findSmallestWeekStart(appUser.get());
        weeklyReviewResponseDTO.setWeekStart(smallestWeekStart);
        //Average cardinal per week
        weeklyReviewResponseDTO.setCardinalPerWeek(getAverageCardinalPerWeek(appUser.get(), smallestWeekStart));
        //Average bloodPressure per week
        weeklyReviewResponseDTO.setBloodPressurePerWeek(getAverageBloodPressurePerWeek(appUser.get(), smallestWeekStart));
        //Average Weight per week
        weeklyReviewResponseDTO.setAverageWeightRecordPerWeek(getAverageWeightPerWeek(appUser.get(), smallestWeekStart));
        //Average Mental per week
        weeklyReviewResponseDTO.setAverageMentalRecordPerWeek(getMentalPerWeek(appUser.get(), smallestWeekStart));
        //Review Activity per week
        weeklyReviewResponseDTO.setActivityRecordPerWeek(getActivityPerWeek(appUser.get(), smallestWeekStart));
        //Average diet per week
        weeklyReviewResponseDTO.setAverageDietRecordPerWeek(getAverageDietPerWeek(appUser.get(), smallestWeekStart));
        //get Done and Total medicine per week
        weeklyReviewResponseDTO.setMedicineRecordPerWeek(getMedicinePerWeek(appUser.get(),smallestWeekStart));
        //Average step per week
        weeklyReviewResponseDTO.setAverageStepRecordPerWeek(getAverageStepPerWeek(appUser.get(),smallestWeekStart));
        return weeklyReviewResponseDTO;
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
    public WeekReview getDataReviewForWeek(String weekStart) throws ParseException {
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
            throw new AppException(ErrorCode.WEEK_REVIEW_EXIST);
        }
        return weekReviewExist.get();
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
                                && record.getAppUserId() == appUser)
                .collect(Collectors.toList());
        double sum = 0;
        for (MentalRecord record : mentalRecordList) {
            if(record.isStatus() == true){
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
                                && record.getAppUserId() == appUser)
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