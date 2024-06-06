package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.AccountResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AuthenticationResponseDto createStaff(WebUserRequestDTO account);
    Optional<Account> getAccountById(Integer id);
    Optional<Account> getAccountByEmail(String email);
    List<Account> getAllAccounts();
    Account updateAccount(Account account);
    void deleteAccount(Integer id);

    AuthenticationResponseDto loginStaff(LoginDto loginDto);
}
