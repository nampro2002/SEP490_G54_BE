package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicineTypeRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineTypeResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;

import java.util.List;
import java.util.Optional;

public interface MedicineTypeService {
    MedicineTypeResponseDTO createMedicineType(MedicineTypeRequestDTO medicineType);
    MedicineTypeResponseDTO getMedicineTypeById(Integer id);
    MedicineType getMedicineTypeEntityById(Integer id);
    ResponsePaging<List<MedicineTypeResponseDTO>> getAllMedicineTypes(Integer pageNo, String search);
    MedicineTypeResponseDTO updateMedicineType(Integer id,MedicineTypeRequestDTO medicineType);
    MedicineTypeResponseDTO deleteMedicineType(Integer id);

    List<MedicineTypeResponseDTO> getAllMedicineTypesMobile();
}