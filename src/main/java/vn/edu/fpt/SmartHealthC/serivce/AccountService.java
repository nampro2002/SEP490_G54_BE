package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UpdatePasswordRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;

import java.util.List;

public interface AccountService {
    void createStaff(WebUserRequestDTO account);
    Account getAccountEntityById(Integer id);
    AccountResponseDTO getAccountById(Integer id);
    AccountResponseDTO getAccountByEmail(String email);
    Account getAccountEntityByEmail(String email);
    List<AccountResponseDTO> getAllAccounts();
    AccountResponseDTO deleteAccount(Integer id);

    boolean activateAccount(Integer id);

    ResponsePaging<List<AppUserResponseDTO>> getPendingAccount(Integer pageNo, TypeAccount type);
    List<AvailableMSResponseDTO> getAvailableMS();

    AccountResponseDTO changePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO);

    ResponsePaging<List<AppUserResponseDTO>> getUserPendingAssign(Integer pageNo);
}
