package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse.*;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.*;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
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

   private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public WeeklyReviewResponseDTO getWeekDate(Integer id) throws ParseException {
        WeeklyReviewResponseDTO weeklyReviewResponseDTO = new WeeklyReviewResponseDTO();

        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //trả về ngày sớm nhất của user
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date smallestWeekStart = formatDate.parse(findSmallestWeekStart(appUser.get()));
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
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date smallestWeekStart = formatDate.parse(findSmallestWeekStart(appUser.get()));
        List<Date> weekStartList = new ArrayList<>();

        //Chuyển smallestWeekStart từ date thành localdate để cộng thêm 7 ngày và thêm vào làm phần tử đầu tiên của mảng date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate firstWeekStart = LocalDate.parse(formatDate.format(smallestWeekStart), formatter);
        weekStartList.add(smallestWeekStart);

        //Lấy ra ngày hiện tại và gán ngày sớm nhất cho datePlus7
        LocalDate today = LocalDate.now();
        LocalDate datePlus7 = firstWeekStart;

        //Vòng lặp cho đến ngày hiện tại
        boolean loopStatus = true;
        for (; loopStatus;) {
            // Cộng thêm 7 ngày vào ngày đầu tiên của user sau 1 vòng lặp
            datePlus7 = datePlus7.plusDays(7);
                // Kiểm tra xem datePlus7 nhỏ hơn ngày hôm nay
                if (datePlus7.isBefore(today) ) {
                    //Chuyển từ localDate thành String và thành Date theo UTC để thêm vào list
                    String formattedDate = datePlus7.format(formatter);
                    weekStartList.add(formatDate.parse(formattedDate));
                }else if(datePlus7.isEqual(today)){ //datePlus7 = và là thứ 2 thì thêm vào list
                    //Ngày hôm nay  là thứ 2 thì check
                    if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
                        //Chuyển từ localDate thành String và thành Date theo UTC để thêm vào list
                        String formattedDate = today.format(formatter);
                        weekStartList.add(formatDate.parse(formattedDate));
                    }
                }else{ // dateplus mà quá lớn hơn hiên tại thì dừng
                    loopStatus = false;
                }
        }
        return weekStartList;
    }


    @Override
    public WeeklyReviewResponseDTO getDataReviewForWeekDate(Integer id, String weekStart) throws ParseException {
        WeeklyReviewResponseDTO weeklyReviewResponseDTO = new WeeklyReviewResponseDTO();

        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        //week start for filter
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date weekStartFilter = formatDate.parse(weekStart);
        weeklyReviewResponseDTO.setWeekStart(weekStartFilter);
        //Average cardinal per week
        weeklyReviewResponseDTO.setCardinalPerWeek(getAverageCardinalPerWeek(appUser.get(), weekStartFilter));
        //Average bloodPressure per week
        weeklyReviewResponseDTO.setBloodPressurePerWeek(getAverageBloodPressurePerWeek(appUser.get(), weekStartFilter));
        //Average Weight per week
        weeklyReviewResponseDTO.setAverageWeightRecordPerWeek(getAverageWeightPerWeek(appUser.get(), weekStartFilter));
        //Average Mental per week
        weeklyReviewResponseDTO.setAverageMentalRecordPerWeek(getMentalPerWeek(appUser.get(), weekStartFilter));
        //Review Activity per week
        weeklyReviewResponseDTO.setActivityRecordPerWeek(getActivityPerWeek(appUser.get(), weekStartFilter));
        //Average diet per week
        weeklyReviewResponseDTO.setAverageDietRecordPerWeek(getAverageDietPerWeek(appUser.get(), weekStartFilter));
        //get Done and Total medicine per week
        weeklyReviewResponseDTO.setMedicineRecordPerWeek(getMedicinePerWeek(appUser.get(),weekStartFilter));
        //Average step per week
        weeklyReviewResponseDTO.setAverageStepRecordPerWeek(getAverageStepPerWeek(appUser.get(),weekStartFilter));
        return weeklyReviewResponseDTO;
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

    private List<Date> getArraySmallestWeekStartByUser(AppUser appUser) {
        List<Date> smallestDateList = new ArrayList<>();

        stepRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId() == appUser)
                .map(StepRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

        cardinalRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId() == appUser)
                .map(CardinalRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

        bloodPressureRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId() == appUser)
                .map(BloodPressureRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       weightRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId() == appUser)
                .map(WeightRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       mentalRecordRepository.findAll().stream()
        .filter(record -> record.getAppUserId() == appUser)
        .map(MentalRecord::getWeekStart)
        .min(Comparator.naturalOrder())
        .ifPresent(smallestDateList::add);

       dietRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId() == appUser)
                .map(DietRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       medicineRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId() == appUser)
                .map(MedicineRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);

       activityRecordRepository.findAll().stream()
                .filter(record -> record.getAppUserId() == appUser)
                .map(ActivityRecord::getWeekStart)
                .min(Comparator.naturalOrder())
                .ifPresent(smallestDateList::add);
       return smallestDateList;
    }

    private String findSmallestWeekStart(AppUser appUser) {
        List<Date> smallestDateList = getArraySmallestWeekStartByUser(appUser);
        if (smallestDateList.isEmpty()) {
            throw new AppException(ErrorCode.USER_WEEK_START_NOT_EXIST);
        }
        return smallestDateList.stream()
                .min(Date::compareTo)
                .orElse(null).toString();
    }
}