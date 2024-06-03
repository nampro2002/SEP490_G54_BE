package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.List;
import java.util.Optional;

@Service
public class WebUserServiceImpl implements WebUserService {
    @Autowired
    private WebUserRepository webUserRepository;

    @Override
    public WebUser createWebUser(WebUser webUser) {
        return webUserRepository.save(webUser);
    }

    @Override
    public Optional<WebUser> getWebUserById(Integer id) {
        return webUserRepository.findById(id);
    }

    @Override
    public List<WebUser> getAllWebUsers() {
        return webUserRepository.findAll();
    }

    @Override
    public WebUser updateWebUser(WebUser webUser, Integer id) {
        return webUserRepository.save(webUser);
    }

    @Override
    public void deleteWebUser(Integer id) {
        WebUser webUser = getWebUserById(id).orElseThrow();
        ///
        webUserRepository.deleteById(id);
    }
}
