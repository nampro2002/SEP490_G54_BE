package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordListResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicineTypeRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineRecordServiceImpl implements MedicineRecordService {
    @Autowired
    private MedicineRecordRepository medicineRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MedicineTypeRepository medicineTypeRepository;

    @Override
    public MedicineRecordResponseDTO createMedicineRecord(MedicineRecordDTO medicineRecordDTO) {
        MedicineRecord medicineRecord = MedicineRecord.builder()
                .weekStart(medicineRecordDTO.getWeekStart())
                .date(medicineRecordDTO.getDate())
                .status(false)
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(medicineRecordDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        medicineRecord.setAppUserId(appUser.get());
        Optional<MedicineType> medicineType = medicineTypeRepository.findById(medicineRecordDTO.getMedicineTypeId());
        if (medicineType.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }
        medicineRecord.setMedicineType(medicineType.get());
        medicineRecord = medicineRecordRepository.save(medicineRecord);
        return MedicineRecordResponseDTO.builder()
                .id(medicineRecord.getId())
                .appUserName(medicineRecord.getAppUserId().getName())
                .medicineType(medicineRecord.getMedicineType().getTitle())
                .weekStart(medicineRecord.getWeekStart())
                .date(medicineRecord.getDate())
                .status(medicineRecord.getStatus())
                .build();
    }

    @Override
    public MedicineRecordResponseDTO getMedicineRecordById(Integer id) {
        Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findById(id);
        if (medicineRecord.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }

        return MedicineRecordResponseDTO.builder()
                .id(medicineRecord.get().getId())
                .appUserName(medicineRecord.get().getAppUserId().getName())
                .medicineType(medicineRecord.get().getMedicineType().getTitle())
                .weekStart(medicineRecord.get().getWeekStart())
                .date(medicineRecord.get().getDate())
                .status(medicineRecord.get().getStatus())
                .build();
    }

    @Override
    public MedicineRecord getMedicineRecordEntityById(Integer id) {
        Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findById(id);
        if (medicineRecord.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }

        return medicineRecord.get();
    }

    @Override
    public List<MedicineRecordListResDTO> getAllMedicineRecords(Integer userId) {
        List<Date> medicineDate = medicineRecordRepository.findDistinctDate(userId);
        List<MedicineRecordListResDTO> responseDTOList = new ArrayList<>();
        for (Date week : medicineDate) {
            MedicineRecordListResDTO medicineRecordListResDTO = MedicineRecordListResDTO.builder()
                    .date(week)
                    .build();
            responseDTOList.add(medicineRecordListResDTO);
        }
        for (MedicineRecordListResDTO medicineRecord : responseDTOList) {
            List<MedicineRecord> medicineRecords = medicineRecordRepository.findByDate(medicineRecord.getDate(), userId);
            int count = 0;
            for (MedicineRecord record : medicineRecords) {
                if (record.getStatus()) {
                    count++;
                }
            }
            medicineRecord.setMedicineStatus(count+"/"+medicineRecords.size());
        }
        return responseDTOList;
    }

    @Override
    public MedicineRecordResponseDTO updateMedicineRecord(Integer id, MedicineRecordDTO medicineRecordDTO) {
        MedicineRecord medicineRecord = getMedicineRecordEntityById(id);
        medicineRecord.setWeekStart(medicineRecordDTO.getWeekStart());
        medicineRecord.setDate(medicineRecordDTO.getDate());
        medicineRecord.setStatus(medicineRecordDTO.getStatus());
        medicineRecordRepository.save(medicineRecord);
        return MedicineRecordResponseDTO.builder()
                .id(medicineRecord.getId())
                .appUserName(medicineRecord.getAppUserId().getName())
                .medicineType(medicineRecord.getMedicineType().getTitle())
                .weekStart(medicineRecord.getWeekStart())
                .date(medicineRecord.getDate())
                .status(medicineRecord.getStatus())
                .build();
    }

    @Override
    public MedicineRecordResponseDTO deleteMedicineRecord(Integer id) {
        MedicineRecord medicineRecord = getMedicineRecordEntityById(id);
        medicineRecordRepository.deleteById(id);
        return MedicineRecordResponseDTO.builder()
                .id(medicineRecord.getId())
                .weekStart(medicineRecord.getWeekStart())
                .date(medicineRecord.getDate())
                .status(medicineRecord.getStatus())
                .build();
    }
}
