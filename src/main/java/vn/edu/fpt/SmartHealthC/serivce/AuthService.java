package vn.edu.fpt.SmartHealthC.serivce;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LoginDto;
import vn.edu.fpt.SmartHealthC.domain.dto.request.RegisterDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.RefreshTokenResponseDto;

import java.text.ParseException;

public interface AuthService {
    AuthenticationResponseDto login(LoginDto request) throws ParseException;

    void register(RegisterDto request);

    String sendEmailCode(String email);

    RefreshTokenResponseDto refreshToken(String refreshToken,
    HttpServletRequest request, HttpServletResponse response) throws ParseException;
}
