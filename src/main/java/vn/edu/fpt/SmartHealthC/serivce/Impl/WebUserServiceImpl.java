package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserUpdateRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WebUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.List;
import java.util.Optional;

@Service
public class WebUserServiceImpl implements WebUserService {
    @Autowired
    private WebUserRepository webUserRepository;

//    @Override
//    public WebUser createWebUser(WebUser webUser) {
//        return webUserRepository.save(webUser);
//    }

    @Override
    public WebUser getWebUserById(Integer id) {
        Optional<WebUser> webUser = webUserRepository.findById(id);
        if (webUser.isEmpty()) {
            throw new AppException(ErrorCode.WEB_USER_NOT_FOUND);
        }
        return webUser.get();
    }

    @Override
    public List<WebUser> getAllWebUsers() {
        return webUserRepository.findAll();
    }

    @Override
    public List<WebUser> getAllUnDeletedMS() {
        return webUserRepository.findAllUnDeletedMS();
    }

    @Override
    public WebUser getWebUserByEmail(String email) {
        Optional<WebUser> webUser = webUserRepository.findByEmail(email);
        if (webUser.isEmpty()) {
            throw new AppException(ErrorCode.WEB_USER_NOT_FOUND);
        }
        return webUser.get();
    }

    @Override
    public WebUserResponseDTO updateWebUser(WebUserUpdateRequestDTO webUserRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<WebUser> appUser = webUserRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.WEB_USER_NOT_FOUND);
        }
        Integer id = appUser.get().getId();
        WebUser webUser = getWebUserById(id);
        webUser.setDob(webUserRequestDTO.getDob());
        webUser.setGender(webUserRequestDTO.getGender());
        webUser.setUserName(webUserRequestDTO.getName());
        webUser.setPhoneNumber(webUserRequestDTO.getPhoneNumber());
        webUser = webUserRepository.save(webUser);
        return WebUserResponseDTO.builder()
                .accountId(webUser.getAccountId().getId())
                .email(webUser.getAccountId().getEmail())
                .webUserId(webUser.getId())
                .name(webUser.getUserName())
                .phoneNumber(webUser.getPhoneNumber())
                .gender(webUser.isGender())
                .dob(webUser.getDob()).build();
    }

/*    @Override
    public WebUser deleteWebUser(Integer id) {
        WebUser webUser = webUserRepository.findById(id);
        webUserRepository.deleteById(id);
        return webUser;
    }*/
}
