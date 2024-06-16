package vn.edu.fpt.SmartHealthC.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.RefreshTokenRepository;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JwtProvider jwtService;

   private final UserDetailsService userDetailsService;

   private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // check if there is a token
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //validate jwt
        jwt = authHeader.substring(7);
        Optional<RefreshToken> accessToken = refreshTokenRepository.findRecordByAcToken(jwt);
        if(accessToken.isEmpty()) {
//            System.out.println("STATUS: TOKEN KHÔNG TỒN TẠI");
            throw new AppException(ErrorCode.ACCESS_TOKEN_NOT_EXIST);
        }

        userEmail = jwtService.extractUserName(jwt);// verify jwt
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetail = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetail)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetail,
                        null,
                        userDetail.getAuthorities()
                );
                authToken.setDetails(
                        // WebAuthenticationDetailsSource bridge between servlet classes and Spring classes, convert
                        // an instance of HttpServletRequest class into an instance of the WebAuthenticationDetails class
                        // so that spring bôt can handle the request, build an buildDetails for spring security to authorize the request.
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}
