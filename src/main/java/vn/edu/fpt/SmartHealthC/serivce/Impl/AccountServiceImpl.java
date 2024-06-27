package vn.edu.fpt.SmartHealthC.serivce.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
import vn.edu.fpt.SmartHealthC.domain.entity.Question;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
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
    public boolean activateAccount(Integer id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.APP_USER_NOT_FOUND));
        if (appUser.getAccountId().getIsActive()) {
            throw new AppException(ErrorCode.ACCOUNT_ACTIVATED);
        }
        Account account = accountRepository.findById(appUser.getAccountId().getId()).orElseThrow();
        account.setIsActive(true);
        accountRepository.save(account);
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
    public Account changePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = getAccountByEmail(email);
        if (!passwordEncoder.matches(updatePasswordRequestDTO.getOldPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.WRONG_OLD_PASSWORD);
        }
        account.setPassword(passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword()));
        return accountRepository.save(account);
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
                .totalItems((listResponse.size()))
                .dataResponse(listResponse)
                .build();
    }

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
    public Account getAccountById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return account.get();
    }

    @Override
    public Account getAccountByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return account.get();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAllNotDeleted();
    }


//    @Override
//    public Account updateAccount(Account account) {
//        return accountRepository.save(account);
//    }

    @Override
    public Account deleteAccount(Integer id) {
        Account account = getAccountById(id);
        if (account.isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }
        account.setDeleted(true);
        accountRepository.save(account);
        return account;
    }


}
