package vn.edu.fpt.SmartHealthC.serivce.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    private final WebUserService webUserService;

    @Override
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser getAccountById(Integer id) {
        Optional<AppUser> account = appUserRepository.findById(id);
        if (account.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        return account.get();
    }

    @Override
    public void assignPatientToDoctor(AssignRequestDTO assignRequestDTO) {
        AppUser appUser = getAccountById(assignRequestDTO.getAppUserId());
        WebUser webUser = webUserService.getWebUserById(assignRequestDTO.getWebUserId());
        appUser.setWebUser(webUser);
        appUserRepository.save(appUser);
    }
}
