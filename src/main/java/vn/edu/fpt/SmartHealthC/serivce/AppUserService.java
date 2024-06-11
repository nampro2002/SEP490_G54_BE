package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.List;

public interface AppUserService {
    List<AppUser> findAll();

    void assignPatientToDoctor(AssignRequestDTO assignRequestDTO);
    AppUser getAccountById(Integer id);
}
