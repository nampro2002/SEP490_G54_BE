package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypeRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineTypeResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MedicineTypeRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineTypeServiceImpl implements MedicineTypeService {

    @Autowired
    private MedicineTypeRepository medicineTypeRepository;

    @Override
    public MedicineTypeResponseDTO createMedicineType(MedicineTypeRequestDTO medicineTypeRequestDTO) {
        MedicineType medicineType =  MedicineType.builder()
                .title(medicineTypeRequestDTO.getTitle())
                .description(medicineTypeRequestDTO.getDescription())
                .isDeleted(false)
                .build();
        medicineType =  medicineTypeRepository.save(medicineType);
        return MedicineTypeResponseDTO.builder()
                .id(medicineType.getId())
                .title(medicineType.getTitle())
                .description(medicineType.getDescription())
                .isDeleted(medicineType.isDeleted())
                .build();
    }

    @Override
    public MedicineTypeResponseDTO getMedicineTypeById(Integer id) {

        Optional<MedicineType> medicineType = medicineTypeRepository.findById(id);
        if(medicineType.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }

        return MedicineTypeResponseDTO.builder()
                .id(medicineType.get().getId())
                .title(medicineType.get().getTitle())
                .description(medicineType.get().getDescription())
                .isDeleted(medicineType.get().isDeleted())
                .build();
    }
    @Override
    public MedicineType getMedicineTypeEntityById(Integer id) {

        Optional<MedicineType> medicineType = medicineTypeRepository.findById(id);
        if(medicineType.isEmpty()) {
            throw new AppException(ErrorCode.MEDICINE_TYPE_NOT_FOUND);
        }

        return medicineType.get();
    }

    @Override
    public ResponsePaging<List<MedicineTypeResponseDTO>> getAllMedicineTypes(Integer pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicineType> pagedResult = medicineTypeRepository.findAllNotDeleted(paging, search);
        List<MedicineType> medicineTypeList= new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicineTypeList = pagedResult.getContent();
        }
        List<MedicineTypeResponseDTO> medicineTypeResponseDTOList = new ArrayList<>();
        for(MedicineType medicineType:medicineTypeList){
            medicineTypeResponseDTOList.add(MedicineTypeResponseDTO.builder()
                    .id(medicineType.getId())
                    .title(medicineType.getTitle())
                    .description(medicineType.getDescription())
                    .isDeleted(medicineType.isDeleted())
                    .build());
        }
        return ResponsePaging.<List<MedicineTypeResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(medicineTypeResponseDTOList)
                .build();
    }

    @Override
    public MedicineTypeResponseDTO updateMedicineType(Integer id,MedicineTypeRequestDTO medicineTypeRequestDTO) {
        MedicineType medicineType = getMedicineTypeEntityById(id);
        medicineType.setDeleted(medicineTypeRequestDTO.isDeleted());
        medicineType.setDescription(medicineTypeRequestDTO.getDescription());
        medicineType.setTitle(medicineTypeRequestDTO.getTitle());
        medicineType = medicineTypeRepository.save(medicineType);
        return MedicineTypeResponseDTO.builder()
                .id(medicineType.getId())
                .title(medicineType.getTitle())
                .description(medicineType.getDescription())
                .isDeleted(medicineType.isDeleted())
                .build();
    }

    @Override
    public MedicineTypeResponseDTO deleteMedicineType(Integer id) {
        MedicineType medicineType = getMedicineTypeEntityById(id);
        medicineTypeRepository.deleteById(id);
        return MedicineTypeResponseDTO.builder()
                .id(medicineType.getId())
                .title(medicineType.getTitle())
                .description(medicineType.getDescription())
                .isDeleted(medicineType.isDeleted())
                .build();
    }

    @Override
    public List<MedicineTypeResponseDTO> getAllMedicineTypesMobile() {
        List<MedicineType> medicineTypeList = medicineTypeRepository.findAllNotDeleted();
        List<MedicineTypeResponseDTO> medicineTypeResponseDTOList = new ArrayList<>();
        for(MedicineType medicineType:medicineTypeList){
            medicineTypeResponseDTOList.add(MedicineTypeResponseDTO.builder()
                    .id(medicineType.getId())
                    .title(medicineType.getTitle())
                    .description(medicineType.getDescription())
                    .isDeleted(medicineType.isDeleted())
                    .build());
        }
        return medicineTypeResponseDTOList;
    }
}