package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.ActivityRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.ActivityRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityRecordServiceImpl implements ActivityRecordService {

    @Autowired
    private ActivityRecordRepository activityRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public ActivityRecord createActivityRecord(ActivityRecordDTO activityRecordDTO)
    {
        ActivityRecord activityRecord =  ActivityRecord.builder()
                .duration(activityRecordDTO.getDuration())
                .weekStart(activityRecordDTO.getWeekStart())
                .date(activityRecordDTO.getDate())
                .type(activityRecordDTO.getType())
                .status(activityRecordDTO.isStatus()).build();
        Optional<AppUser> appUser = appUserRepository.findById(activityRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        activityRecord.setAppUserId(appUser.get());
        return  activityRecordRepository.save(activityRecord);
    }

    @Override
    public Optional<ActivityRecord> getActivityRecordById(Integer id) {
        Optional<ActivityRecord> activityRecordId = activityRecordRepository.findById(id);
        if(activityRecordId.isEmpty()) {
            throw new AppException(ErrorCode.ACTIVITY_RECORD_NOT_FOUND);
        }

        return activityRecordId;
    }

    @Override
    public List<ActivityRecord> getAllActivityRecords() {
        return activityRecordRepository.findAll();
    }

    @Override
    public ActivityRecord updateActivityRecord(ActivityRecordDTO activityRecordDTO) {
            ActivityRecord activityRecord =  ActivityRecord.builder()
                    .duration(activityRecordDTO.getDuration())
                    .weekStart(activityRecordDTO.getWeekStart())
                    .date(activityRecordDTO.getDate())
                    .type(activityRecordDTO.getType())
                    .status(activityRecordDTO.isStatus()).build();
        Optional<ActivityRecord> activityRecordId = activityRecordRepository.findById(activityRecordDTO.getId());
        Optional<AppUser> appUser = appUserRepository.findById(activityRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        if(activityRecordId.isEmpty()) {
            throw new AppException(ErrorCode.ACTIVITY_RECORD_NOT_FOUND);
        }
        activityRecord.setAppUserId(appUser.get());
        return  activityRecordRepository.save(activityRecord);
    }

    @Override
    public void deleteActivityRecord(Integer id) {
        activityRecordRepository.deleteById(id);
    }

}