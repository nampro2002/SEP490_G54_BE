package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AppUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {
    List<AppUser> findAll();

    AppUserAssignResponseDTO assignPatientToMs(AssignRequestDTO assignRequestDTO);
    AppUserDetailResponseDTO getAppUserDetailById(Integer id);

    ResponsePaging<List<AppUserResponseDTO>> getListAppUser(Integer pageNo, String search);

    AppUserDetailResponseDTO updateAppUser(Integer id, AppUserRequestDTO appUserDTO);

    AppUser findAppUserByEmail(String email);
    Optional<AppUser> findAppUserEntityById(Integer id);
    List<AppUser> getListAppUser();

    List<AppUser> findAllByWebUserId(Integer id);

    AppUserNameHeightWeightResponseDTO getAppUserNameHeightWeight(TypeLanguage language);
}
