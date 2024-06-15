package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.AppUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserDetailResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.List;

public interface AppUserService {
    List<AppUser> findAll();

    void assignPatientToDoctor(AssignRequestDTO assignRequestDTO);
    AppUserDetailResponseDTO getAppUserDetailById(Integer id);

    ResponsePaging<List<AppUserResponseDTO>> getListAppUser(Integer pageNo, String search, Integer id);

    AppUserDetailResponseDTO updateAppUser(Integer id, AppUserRequestDTO appUserDTO);
}
