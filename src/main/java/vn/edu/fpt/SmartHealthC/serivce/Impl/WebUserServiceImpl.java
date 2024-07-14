package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserUpdateRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRuleResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WebUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.ArrayList;
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
    public ResponsePaging<List<WebUserResponseDTO>> getListDoctorNotDelete(Integer pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<WebUser> pagedResult = webUserRepository.findAllUnDeletedDoctor(TypeAccount.DOCTOR, paging);
        List<WebUser> webUserList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            webUserList = pagedResult.getContent();
        }
        List<WebUserResponseDTO> webUserResponseDTOS = new ArrayList<>();
        for (WebUser webUser : webUserList) {
            webUserResponseDTOS.add(WebUserResponseDTO.builder()
                    .accountId(webUser.getAccountId().getId())
                    .email(webUser.getAccountId().getEmail())
                    .webUserId(webUser.getId())
                    .name(webUser.getUserName())
                    .phoneNumber(webUser.getPhoneNumber()).build());
        }
        return ResponsePaging.<List<WebUserResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(webUserResponseDTOS)
                .build();
    }

    @Override
    public ResponsePaging<List<WebUserResponseDTO>> getListMsAdminNotDelete(Integer pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<WebUser> pagedResult = webUserRepository.findAllUnDeletedNotDoctor(TypeAccount.DOCTOR, paging);
        List<WebUser> webUserList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            webUserList = pagedResult.getContent();
        }
        List<WebUserResponseDTO> webUserResponseDTOS = new ArrayList<>();
        for (WebUser webUser : webUserList) {
            webUserResponseDTOS.add(WebUserResponseDTO.builder()
                    .accountId(webUser.getAccountId().getId())
                    .email(webUser.getAccountId().getEmail())
                    .webUserId(webUser.getId())
                    .dob(webUser.getDob())
                    .name(webUser.getUserName())
                    .phoneNumber(webUser.getPhoneNumber()).build());
        }
        return ResponsePaging.<List<WebUserResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(webUserResponseDTOS)
                .build();
    }

    @Override
    public WebUserResponseDTO getDetailCurrentWebUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<WebUser> webUser = webUserRepository.findByEmail(email);
        if (webUser.isEmpty()) {
            throw new AppException(ErrorCode.WEB_USER_NOT_FOUND);
        }
        return WebUserResponseDTO.builder()
                .accountId(webUser.get().getAccountId().getId())
                .email(webUser.get().getAccountId().getEmail())
                .webUserId(webUser.get().getId())
                .name(webUser.get().getUserName())
                .phoneNumber(webUser.get().getPhoneNumber())
                .gender(webUser.get().isGender())
                .dob(webUser.get().getDob()).build();
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

    @Override
    public WebUserResponseDTO deleteWebUser(Integer id) {
        WebUser webUser = getWebUserById(id);
        webUser.getAccountId().setDeleted(true);
        webUser = webUserRepository.save(webUser);
        return WebUserResponseDTO.builder()
                .accountId(webUser.getAccountId().getId())
                .email(webUser.getAccountId().getEmail())
                .webUserId(webUser.getId())
                .name(webUser.getUserName())
                .phoneNumber(webUser.getPhoneNumber()).build();

    }
}
