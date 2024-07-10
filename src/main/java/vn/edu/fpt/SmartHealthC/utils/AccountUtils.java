package vn.edu.fpt.SmartHealthC.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AccountUtils {
     public static  AppUser getAccountAuthen (AppUserRepository appUserRepository){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String email = authentication.getName();
         Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
         if(appUser.isEmpty()){
             throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
         }
         return appUser.get();
     }


}
