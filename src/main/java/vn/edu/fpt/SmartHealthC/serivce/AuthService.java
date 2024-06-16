package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RefreshTokenRequestDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.RefreshTokenResponseDto;

public interface AuthService {
    AuthenticationResponseDto login(LoginDto request);

    void register(RegisterDto request);

    String sendEmailCode(String email);

    RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto token);
}
