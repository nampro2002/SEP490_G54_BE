package vn.edu.fpt.SmartHealthC.serivce.Impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UpdatePasswordRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AccountResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AvailableMSResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.UserWeek1Information;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.UserWeek1InformationRepository;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;
@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private WebUserRepository webUserRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private WebUserService webUserService;

    @MockBean
    private AppUserRepository appUserRepository;

    @Mock
    private UserWeek1InformationRepository userWeek1InformationRepository;

    private AppUser appUser;
    private Account account;
    private UpdatePasswordRequestDTO updatePasswordRequestDTO;
    private UserWeek1Information userWeek1Information;

    @BeforeEach
    void initData() {
        account = Account.builder()
                .Id(1)
                .email("john.doe@example.com")
                .password("encoded_password")
                .type(TypeAccount.USER)
                .isActive(true)
                .isDeleted(false)
                .build();

        appUser = AppUser.builder()
                .id(1)
                .accountId(account)
                .name("John Doe")
                .dob(Date.valueOf(LocalDate.of(1990, 1, 1)))
                .gender(true)
                .phoneNumber("1234567890")
                .medicalSpecialistNote("Note")
                .build();

        updatePasswordRequestDTO = UpdatePasswordRequestDTO.builder()
                .oldPassword("old_password")
                .newPassword("New@1234")
                .build();
        userWeek1Information = new UserWeek1Information(
                1,
                appUser,
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                1,
                2,
                3,
                4,
                5,
                "123",
                "123",
                "123",
                "123",
                true,
                "123",
                "123",
                true,
                true,
                true,
                true,
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                "123"
                // ... các giá trị khác
        );
    }

    @Test
    void activateAccount_accountExistsAndNotActivated_success() {
        // GIVEN
        AppUser appUser = AppUser.builder()
                .id(1)
                .accountId(account)
                .webUser(null)
                .build();

        account.setActive(false);
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(appUserRepository.findByAccount(account)).thenReturn(Optional.of(appUser));
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
        when(userWeek1InformationRepository.findByAppUser(appUser)).thenReturn(Optional.ofNullable(userWeek1Information));
        when(userWeek1InformationRepository.save(userWeek1Information)).thenReturn(userWeek1Information);
        // WHEN
        boolean result = accountService.activateAccount(1);

        // THEN
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(account.isActive()).isTrue();
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void activateAccount_accountAlreadyActivated_throwsException() {
        // GIVEN
        AppUser appUser = AppUser.builder()
                .id(1)
                .accountId(account)
                .webUser(WebUser.builder().build())
                .build();
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(appUserRepository.findByAccount(account)).thenReturn(Optional.of(appUser));
        when(userWeek1InformationRepository.findByAppUser(appUser)).thenReturn(Optional.ofNullable(userWeek1Information));
        when(userWeek1InformationRepository.save(userWeek1Information)).thenReturn(userWeek1Information);

        // WHEN
        var exception = assertThrows(AppException.class, () -> accountService.activateAccount(1));

        // THEN
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_ACTIVATED);
    }
    @Test
    void deleteAccount_validId_success() {
        // GIVEN
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        // WHEN
        AccountResponseDTO result = accountService.deleteAccount(1);

        // THEN
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getIsActive()).isTrue();
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void deleteAccount_accountAlreadyDeleted_throwsException() {
        // GIVEN
        account.setDeleted(true);
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        // WHEN
        var exception = assertThrows(AppException.class, () -> accountService.deleteAccount(1));

        // THEN
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_DELETED);
    }
    @Test
    void getAccountById_existingId_success() {
        // GIVEN
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        // WHEN
        Account result = accountService.getAccountEntityById(1);

        // THEN
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void getAccountById_nonExistingId_throwsException() {
        // GIVEN
        when(accountRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN
        var exception = assertThrows(AppException.class, () -> accountService.getAccountById(1));

        // THEN
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
    }
    @Test
    void getAllAccounts_multipleAccounts_success() {
        // GIVEN
        List<Account> accounts = List.of(
                Account.builder().Id(1).build(),
                Account.builder().Id(2).build()
        );
        when(accountRepository.findAllNotDeleted()).thenReturn(accounts);

        // WHEN
        List<AccountResponseDTO> result = accountService.getAllAccounts();

        // THEN
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getAllAccounts_noAccounts_emptyList() {
        // GIVEN
        when(accountRepository.findAllNotDeleted()).thenReturn(Collections.emptyList());

        // WHEN
        List<AccountResponseDTO> result = accountService.getAllAccounts();

        // THEN
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void getPendingAccount_validPageNo_success() {
        // GIVEN
        List<AppUser> appUsers = List.of(appUser);
        Page<AppUser> page = new PageImpl<>(appUsers);
        when(appUserRepository.findAllInactiveAccountUser(any(), any(Pageable.class))).thenReturn(page);

        // WHEN
        var response = accountService.getPendingAccount(0,TypeAccount.USER);

        // THEN
        Assertions.assertThat(response.getTotalItems()).isEqualTo(1);
        Assertions.assertThat(response.getDataResponse().get(0).getAppUserId()).isEqualTo(appUser.getId());
    }

    @Test
    void getPendingAccount_invalidPageNo_emptyResult() {
        // GIVEN
        when(appUserRepository.findAllInactiveAccountUser(any(), any(Pageable.class))).thenReturn(Page.empty());

        // WHEN
        var response = accountService.getPendingAccount(0,TypeAccount.USER);

        // THEN
        Assertions.assertThat(response.getTotalItems()).isEqualTo(0);
        Assertions.assertThat(response.getDataResponse()).isEmpty();
    }

    @Test
    void getAvailableMS_success() {
        // GIVEN
        WebUser webUser = WebUser.builder()
                .id(1)
                .userName("Medical Specialist")
                .appUserList(List.of(appUser))
                .build();
        List<WebUser> webUsers = List.of(webUser);
        when(webUserService.getAllUnDeletedMS()).thenReturn(webUsers);

        // WHEN
        var response = accountService.getAvailableMS();

        // THEN
        Assertions.assertThat(response.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(response.get(0).getMedicalSpecialistName()).isEqualTo("Medical Specialist");
    }

    @Test
    void getAvailableMS_noMedicalSpecialist_emptyResult() {
        // GIVEN
        when(webUserService.getAllUnDeletedMS()).thenReturn(Collections.emptyList());

        // WHEN
        var response = accountService.getAvailableMS();

        // THEN
        Assertions.assertThat(response).isEmpty();
    }

    @Test
    void getUserPendingAssign_validPageNo_success() {
        // GIVEN
        List<AppUser> appUsers = List.of(appUser);
        Page<AppUser> page = new PageImpl<>(appUsers);
        when(appUserRepository.findAllAccountUserNotAssign(any(Pageable.class))).thenReturn(page);

        // WHEN
        var response = accountService.getUserPendingAssign(0);

        // THEN
        Assertions.assertThat(response.getTotalItems()).isEqualTo(1);
        Assertions.assertThat(response.getDataResponse().get(0).getAppUserId()).isEqualTo(appUser.getId());
    }

    @Test
    void getUserPendingAssign_invalidPageNo_emptyResult() {
        // GIVEN
        when(appUserRepository.findAllAccountUserNotAssign(any(Pageable.class))).thenReturn(Page.empty());

        // WHEN
        var response = accountService.getUserPendingAssign(0);

        // THEN
        Assertions.assertThat(response.getTotalItems()).isEqualTo(0);
        Assertions.assertThat(response.getDataResponse()).isEmpty();
    }

    @Test
    void createStaff_validRequest_success() {
        // GIVEN
        WebUserRequestDTO request = WebUserRequestDTO.builder()
                .email("new.staff@example.com")
                .password("Staff@1234")
                .gender(true)
                .typeAccount(TypeAccount.USER)
                .username("newstaff")
                .phoneNumber("0987654321")
                .build();
        Account newAccount = Account.builder()
                .Id(2)
                .email("new.staff@example.com")
                .password("encoded_password")
                .type(TypeAccount.USER)
                .isActive(true)
                .isDeleted(false)
                .build();
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(accountRepository.save(any(Account.class))).thenReturn(newAccount);
        when(webUserRepository.save(any(WebUser.class))).thenReturn(new WebUser());

        // WHEN
        accountService.createStaff(request);

        // THEN
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(webUserRepository, times(1)).save(any(WebUser.class));
    }

    @Test
    void createStaff_emailExists_throwsException() {
        // GIVEN
        WebUserRequestDTO request = WebUserRequestDTO.builder()
                .email("existing.staff@example.com")
                .password("Staff@1234")
                .typeAccount(TypeAccount.USER)
                .username("existingstaff")
                .phoneNumber("0987654321")
                .build();
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));

        // WHEN
        var exception = assertThrows(AppException.class, () -> accountService.createStaff(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.EMAIL_EXISTED);
    }

    @Test
    void getAccountByEmail_existingEmail_success() {
        // GIVEN
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));

        // WHEN
        var result = accountService.getAccountByEmail("john.doe@example.com");

        // THEN
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void getAccountByEmail_nonExistingEmail_throwsException() {
        // GIVEN
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // WHEN
        var exception = assertThrows(AppException.class, () -> accountService.getAccountByEmail("nonexistent@example.com"));

        // THEN
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
    }

    @Test
    void changePassword_validRequest_success() {
        // GIVEN
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(account.getEmail());
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("new_encoded_password");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // WHEN
        accountService.changePassword(updatePasswordRequestDTO);

        // THEN
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void changePassword_wrongOldPassword_throwsException() {
        // GIVEN
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(account.getEmail());
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // WHEN
        var exception = assertThrows(AppException.class, () -> accountService.changePassword(updatePasswordRequestDTO));

        // THEN
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.WRONG_OLD_PASSWORD);
    }


}