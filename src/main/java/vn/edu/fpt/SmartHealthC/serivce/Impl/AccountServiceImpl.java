package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UpdatePasswordRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
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
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private WebUserService webUserService;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserWeek1InformationRepository userWeek1InformationRepository;
//    @Override
//    public AuthenticationResponseDto loginStaff(LoginDto request) {
//        Optional<Account> optionalUser = accountRepository.findByEmail(request.getEmail());
//        if (optionalUser.isEmpty()) {
//            throw new AppException(ErrorCode.CREDENTIAL_INVALID);
//        }
//        Account existingUser = optionalUser.get();
//        //check password
//        if (!passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
//            throw new AppException(ErrorCode.CREDENTIAL_INVALID);
//        }
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        var jwt = jwtProvider.generateToken(optionalUser.get());
//        return AuthenticationResponseDto.builder()
//                .type(optionalUser.get().getType())
//                .idUser(optionalUser.get().getId())
//                .token(jwt)
//                .build();
//    }

    @Override
    @Transactional
    public boolean activateAccount(Integer id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        if (account.isActive()) {
            throw new AppException(ErrorCode.ACCOUNT_ACTIVATED);
        }
        account.setActive(true);

        Optional<AppUser> appUser = appUserRepository.findByAccountId(account.getId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        UserWeek1Information userWeek1Information = new UserWeek1Information();
        userWeek1Information.setAppUserId(appUser.get());
        userWeek1InformationRepository.save(userWeek1Information);
        accountRepository.save(account);
        notificationService.createRecordForAccount(account);
        return true;
    }



    @Override
    public ResponsePaging<List<AppUserResponseDTO>> getPendingAccount(Integer pageNo, TypeAccount type) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<AppUser> pagedResult = appUserRepository.findAllInactiveAccountUser(type, paging);
        List<AppUser> accountList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            accountList = pagedResult.getContent();
        }
        List<AppUserResponseDTO> listResponse = accountList.stream()
//                .filter(record -> (!record.getAccountId().getIsActive() && record.getAccountId().getType().equals(TypeAccount.USER)))
                .map(record -> {
                    AppUserResponseDTO dto = new AppUserResponseDTO();
                    dto.setAccountId(record.getAccountId().getId());
                    dto.setEmail(record.getAccountId().getEmail());
                    dto.setAppUserId(record.getId());
                    dto.setName(record.getName());
                    dto.setCic(record.getCic());
                    dto.setDob(record.getDob());
                    dto.setGender(record.isGender());
                    dto.setPhoneNumber(record.getPhoneNumber());
                    return dto;
                })
                .toList();
        return ResponsePaging.<List<AppUserResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(listResponse)
                .build();
    }
    @Override
    public ResponsePaging<List<WebUserResponseDTO>> getPendingAccountDoctor(Integer pageNo, TypeAccount type) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<WebUser> pagedResult = webUserRepository.findAllInactiveAccountUser(type, paging);
        List<WebUser> accountList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            accountList = pagedResult.getContent();
        }
        List<WebUserResponseDTO> listResponse = accountList.stream()
//                .filter(record -> (!record.getAccountId().getIsActive() && record.getAccountId().getType().equals(TypeAccount.USER)))
                .map(record -> {
                    WebUserResponseDTO dto = new WebUserResponseDTO();
                    dto.setAccountId(record.getAccountId().getId());
                    dto.setEmail(record.getAccountId().getEmail());
                    dto.setWebUserId(record.getId());
                    dto.setName(record.getUserName());
                    dto.setDob(record.getDob());
                    dto.setGender(record.isGender());
                    dto.setPhoneNumber(record.getPhoneNumber());
                    return dto;
                })
                .toList();
        return ResponsePaging.<List<WebUserResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(listResponse)
                .build();
    }


    @Override
    public List<AvailableMSResponseDTO> getAvailableMS() {
        List<WebUser> accountList = webUserService.getAllUnDeletedMS();
        return accountList.stream().filter(record ->
                        (record.getAppUserList().stream().filter(appUser -> !appUser.getAccountId().isDeleted()).count() < 10
                        ))
                .map(record -> {
                    AvailableMSResponseDTO dto = new AvailableMSResponseDTO();
                    dto.setId(record.getId());
                    dto.setMedicalSpecialistName(record.getUserName());
                    return dto;
                }).toList();
    }

    @Override
    public AccountResponseDTO changePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = getAccountEntityByEmail(email);
        if (!passwordEncoder.matches(updatePasswordRequestDTO.getOldPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.WRONG_OLD_PASSWORD);
        }
        account.setPassword(passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword()));
        accountRepository.save(account);
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
                .email(account.getEmail())
                .type(account.getType())
                .isActive(account.isActive())
                .build();
        return accountResponseDTO;
    }

    @Override
    public ResponsePaging<List<AppUserResponseDTO>> getUserPendingAssign(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<AppUser> pagedResult = appUserRepository.findAllAccountUserNotAssign(paging);
        List<AppUser> accountList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            accountList = pagedResult.getContent();
        }
        List<AppUserResponseDTO> listResponse = accountList.stream()
                .map(record -> {
                    AppUserResponseDTO dto = new AppUserResponseDTO();
                    dto.setAccountId(record.getAccountId().getId());
                    dto.setEmail(record.getAccountId().getEmail());
                    dto.setAppUserId(record.getId());
                    dto.setName(record.getName());
                    dto.setCic(record.getCic());
                    dto.setDob(record.getDob());
                    dto.setGender(record.isGender());
                    dto.setPhoneNumber(record.getPhoneNumber());
                    return dto;
                })
                .toList();
        return ResponsePaging.<List<AppUserResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(listResponse)
                .build();
    }

//
//    public List<AccountResponseDTO> getAllAccountAppUser() {
//        List<Account> accountList =  accountRepository.findAllAccountAppUser(TypeAccount.USER);
//        return accountList.stream()
//                .map(record -> {
//                    AccountResponseDTO dto = new AccountResponseDTO();
//                    dto.setEmail(record.getEmail());
//                    dto.setType(record.getType());
//                    dto.setIsActive(record.getIsActive());
//                    return dto;
//                })
//                .toList();
//    }

    @Transactional
    @Override
    public void createStaff(WebUserRequestDTO requestDTO) {
        Optional<Account> existingAccount = accountRepository.findByEmail(requestDTO.getEmail());
        if (existingAccount.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        Account newAccount = Account.builder()
                .email(requestDTO.getEmail())
                .password(encodedPassword)
                .type(requestDTO.getTypeAccount())
                .isActive(true)
                .build();
        newAccount = accountRepository.save(newAccount);
        WebUser newAppUserInfo = WebUser.builder()
                .accountId(newAccount)
                .userName(requestDTO.getUsername())
                .dob(requestDTO.getDob())
                .gender(requestDTO.getGender())
                .phoneNumber(requestDTO.getPhoneNumber())
                .build();
        webUserRepository.save(newAppUserInfo);
    }

    @Override
    public Account getAccountEntityById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return account.get();
    }


    @Override
    public AccountResponseDTO getAccountById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
                .email(account.get().getEmail())
                .type(account.get().getType())
                .isActive(account.get().isActive())
                .build();
        return accountResponseDTO;
    }

    @Override
    public AccountResponseDTO getAccountByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
                .email(account.get().getEmail())
                .type(account.get().getType())
                .isActive(account.get().isActive())
                .build();
        return accountResponseDTO;
    }

    @Override
    public Account getAccountEntityByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return account.get();
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        List<Account> accountList = accountRepository.findAllNotDeleted();
        return accountList.stream()
                .map(record -> {
                    AccountResponseDTO dto = new AccountResponseDTO();
                    dto.setEmail(record.getEmail());
                    dto.setType(record.getType());
                    dto.setIsActive(record.isActive());
                    return dto;
                })
                .toList();
    }


//    @Override
//    public Account updateAccount(Account account) {
//        return accountRepository.save(account);
//    }

    @Override
    public AccountResponseDTO deleteAccount(Integer id) {
        Account account = getAccountEntityById(id);
        if (account.isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }
        account.setDeleted(true);
        accountRepository.save(account);
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
                .email(account.getEmail())
                .type(account.getType())
                .isActive(account.isActive())
                .build();
        return accountResponseDTO;
    }


}
