package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.AccountResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.BadRequestException;
import vn.edu.fpt.SmartHealthC.exception.DataNotFoundException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;

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
    private AppUserRepository appUserRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto loginStaff(LoginDto request){
        Optional<Account> optionalUser = accountRepository.findByEmail(request.getEmail());
        if(optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.CREDENTIAL_INVALID);
        }
        Account existingUser = optionalUser.get();
        //check password
        if(!passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
            throw new AppException(ErrorCode.CREDENTIAL_INVALID);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwt = jwtProvider.generateToken(optionalUser.get());
        return AuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }
    @Override
    public AuthenticationResponseDto createStaff(WebUserRequestDTO account){
        Optional<Account> existingAccount = getAccountByEmail(account.getEmail());

        if(existingAccount.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        Account newAccount = Account.builder()
                .email(account.getEmail())
                .password(encodedPassword)
                .type(TypeAccount.ADMIN)
                .isActive(false)
                .build();
        newAccount = accountRepository.save(newAccount);
        WebUser newAppUserInfo = WebUser.builder()
                .accountId(newAccount)
                .userName(account.getUsername())
                .phoneNumber(account.getPhoneNumber())
                .build();
        webUserRepository.save(newAppUserInfo);
        var jwt = jwtProvider.generateToken(newAccount);
        return AuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }
    @Override
    public Optional<Account> getAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Integer id) {
        Account account = getAccountById(id).orElseThrow();
//        account.setType();
        accountRepository.save(account);
    }


}
