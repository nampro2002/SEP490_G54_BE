package vn.edu.fpt.SmartHealthC.serivce;

import org.springframework.data.jpa.repository.Query;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserUpdateRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WebUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;

import java.util.List;
import java.util.Optional;

public interface WebUserService {
//    WebUser createWebUser(WebUser webUser);
    WebUser getWebUserById(Integer id);

    List<WebUser> getAllWebUsers();
    WebUserResponseDTO updateWebUser(WebUserUpdateRequestDTO webUser);
    List<WebUser> getAllUnDeletedMS();
    WebUser getWebUserByEmail(String email);
}
