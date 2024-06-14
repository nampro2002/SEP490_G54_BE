package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WeightRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.RecordPerDay;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO.WeightResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WeightRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.WeightRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.WeightRecordService;

import java.util.*;

@Service
public class WeightRecordServiceImpl implements WeightRecordService {

    @Autowired
    private WeightRecordRepository weightRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public WeightRecord createWeightRecord(WeightRecordDTO weightRecordDTO)
    {
        WeightRecord weightRecord =  WeightRecord.builder()
                .weekStart(weightRecordDTO.getWeekStart())
                .weight(weightRecordDTO.getWeight())
                .date(weightRecordDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(weightRecordDTO.getAppUserId())
                .orElseThrow(() -> new AppException(ErrorCode.APP_USER_NOT_FOUND));
        weightRecord.setAppUserId(appUser);
        return  weightRecordRepository.save(weightRecord);
    }

    @Override
    public WeightRecord getWeightRecordById(Integer id) {
        Optional<WeightRecord> weightRecord  = weightRecordRepository.findById(id);
        if(weightRecord.isEmpty()){
            throw new AppException(ErrorCode.WEIGHT_RECORD_NOT_FOUND);
        }
        return weightRecord.get();
    }

    @Override
    public List<WeightRecord> getAllWeightRecords() {
        return weightRecordRepository.findAll();
    }

    @Override
    public List<WeightResponseDTO> getWeightRecordList(Integer userId) {
        List<Date> weightRecordWeek = weightRecordRepository.findDistinctWeek(userId);
        List<WeightResponseDTO> responseDTOList = new ArrayList<>();
        for (Date week : weightRecordWeek) {
            WeightResponseDTO weightResponseDTO = WeightResponseDTO.builder()
                    .appUserId(userId)
                    .weekStart(week)
                    .build();
            responseDTOList.add(weightResponseDTO);
        }

        for (WeightResponseDTO record : responseDTOList) {
            List<WeightRecord> weightRecordByWeek = weightRecordRepository.findByWeekStart(record.getWeekStart(), userId);
            List<RecordPerDay> recordPerDayList = new ArrayList<>();
            Float weight = 0f;
            int count = 0;
            for (WeightRecord weightRecord : weightRecordByWeek) {
                RecordPerDay recordPerDay = RecordPerDay.builder()
                        .date(weightRecord.getDate())
                        .weight(weightRecord.getWeight()+"kg")
                        .build();
                recordPerDayList.add(recordPerDay);
                //sort by  Date date;
                recordPerDayList.sort(Comparator.comparing(RecordPerDay::getDate));
                if (weightRecord.getWeight() != null) {
                    weight +=weightRecord.getWeight();
                    count++;
                }
            }
            if (count != 0) {
                weight = weight / count;
                weight = (float) (Math.round(weight * 100) / 100);
            }
            record.setAvgValue(weight + "kg");
            record.setRecordPerDayList(recordPerDayList);
        }
        return responseDTOList;
    }

    @Override
    public WeightRecord updateWeightRecord(Integer id, WeightRecordDTO weightRecordDTO) {

        WeightRecord weightRecord = getWeightRecordById(id);
        weightRecord.setWeight(weightRecordDTO.getWeight());
        return  weightRecordRepository.save(weightRecord);
    }

    @Override
    public WeightRecord deleteWeightRecord(Integer id) {
        WeightRecord weightRecord = getWeightRecordById(id);
        weightRecordRepository.deleteById(id);
        return weightRecord;
    }


}