package vn.edu.fpt.SmartHealthC.serivce.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
import vn.edu.fpt.SmartHealthC.security.JwtProvider;
import vn.edu.fpt.SmartHealthC.serivce.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto login(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var account = repo.findByEmail(request.getEmail())
                .orElseThrow();
        var jwt = jwtProvider.generateToken(account);
        return AuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }

    @Override
    public AuthenticationResponseDto register(RegisterDto request) {
        var account = Account.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(true)
                .type(TypeAccount.USER)
                .build();
        repo.save(account);
        var jwt = jwtProvider.generateToken(account);
        return AuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }
}
