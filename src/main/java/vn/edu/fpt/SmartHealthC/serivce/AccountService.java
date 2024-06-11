package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AvailableMSResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AuthenticationResponseDto createStaff(WebUserRequestDTO account);
    Account getAccountById(Integer id);
    Account getAccountByEmail(String email);
    List<Account> getAllAccounts();
    Account updateAccount(Account account);
    Account deleteAccount(Integer id);

//    AuthenticationResponseDto loginStaff(LoginDto loginDto);

    boolean activateAccount(Integer id);

    List<AppUserResponseDTO> getPendingAccount();
    List<AvailableMSResponseDTO> getAvailableMS();
}
