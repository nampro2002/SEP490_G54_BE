package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.UpdatePasswordRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;

import java.util.List;

public interface AccountService {
    void createStaff(WebUserRequestDTO account);
    Account getAccountById(Integer id);
    Account getAccountByEmail(String email);
    List<Account> getAllAccounts();
    Account updateAccount(Account account);
    Account deleteAccount(Integer id);

//    AuthenticationResponseDto loginStaff(LoginDto loginDto);

    boolean activateAccount(Integer id);

    ResponsePaging<List<AppUserResponseDTO>> getPendingAccount(Integer pageNo);
    List<AvailableMSResponseDTO> getAvailableMS();

    Account changePassword(Integer id, UpdatePasswordRequestDTO updatePasswordRequestDTO);

    ResponsePaging<List<AppUserResponseDTO>> getUserPendingAssign(Integer pageNo);
}
