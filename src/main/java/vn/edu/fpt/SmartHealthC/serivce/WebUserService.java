package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;

import java.util.List;
import java.util.Optional;

public interface WebUserService {
    WebUser createWebUser(WebUser webUser);
    Optional<WebUser> getWebUserById(Integer id);
    List<WebUser> getAllWebUsers();
    WebUser updateWebUser(WebUser webUser, Integer id);
    void deleteWebUser(Integer id);
}
