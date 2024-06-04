package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.RegisterDto;

public interface AuthService {
    AuthenticationResponseDto login(LoginDto request);

    AuthenticationResponseDto register(RegisterDto request);
}
