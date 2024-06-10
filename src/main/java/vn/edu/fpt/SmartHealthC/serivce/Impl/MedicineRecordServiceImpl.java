package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.BloodPressureRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;

import java.util.List;
import java.util.Optional;
@Service
public class MedicineRecordServiceImpl implements MedicineRecordService {
    @Autowired
    private MedicineRecordRepository medicineRecordRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public MedicineRecord createMedicineRecord(MedicineRecordDTO medicineRecordDTO) {
        MedicineRecord medicineRecord =  MedicineRecord.builder()
                .type(medicineRecordDTO.getType())
                .hour(medicineRecordDTO.getHour())
                .weekStart(medicineRecordDTO.getWeekStart())
                .date(medicineRecordDTO.getDate())
                .status(medicineRecordDTO.getStatus())
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(medicineRecordDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        medicineRecord.setAppUserId(appUser.get());
        return medicineRecordRepository.save(medicineRecord);
    }

    @Override
    public MedicineRecord getMedicineRecordById(Integer id) {
        Optional<MedicineRecord> medicineRecord = medicineRecordRepository.findById(id);
        if(medicineRecord.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_NOT_FOUND);
        }

        return medicineRecord.get();
    }

    @Override
    public List<MedicineRecord> getAllMedicineRecords() {
        return medicineRecordRepository.findAll();
    }

    @Override
    public MedicineRecord updateMedicineRecord(Integer id, MedicineRecordDTO medicineRecordDTO) {
        MedicineRecord medicineRecord =  getMedicineRecordById(id);
        medicineRecordDTO.setType(medicineRecordDTO.getType());
        medicineRecordDTO.setHour(medicineRecordDTO.getHour());
        medicineRecordDTO.setWeekStart(medicineRecordDTO.getWeekStart());
        medicineRecordDTO.setDate(medicineRecordDTO.getDate());
        medicineRecordDTO.setStatus(medicineRecordDTO.getStatus());
        return medicineRecordRepository.save(medicineRecord);
    }

    @Override
    public MedicineRecord deleteMedicineRecord(Integer id) {
        MedicineRecord medicineRecord = getMedicineRecordById(id);
        medicineRecordRepository.deleteById(id);
        return medicineRecord;
    }
}
