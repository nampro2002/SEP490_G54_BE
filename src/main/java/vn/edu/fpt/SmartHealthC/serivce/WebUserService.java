package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserUpdateRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WebUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;

import java.util.List;

public interface WebUserService {
//    WebUser createWebUser(WebUser webUser);
    WebUser getWebUserById(Integer id);

    List<WebUser> getAllWebUsers();
    WebUserResponseDTO updateWebUser(WebUserUpdateRequestDTO webUser);
    List<WebUser> getAllUnDeletedMS();
    WebUser getWebUserByEmail(String email);

    ResponsePaging<List<WebUserResponseDTO>> getListDoctorNotDelete(Integer pageNo, String search);

   ResponsePaging<List<WebUserResponseDTO>> getListMsAdminNotDelete(Integer pageNo, String search);

    WebUserResponseDTO getDetailCurrentWebUser();

    WebUserResponseDTO deleteWebUser(Integer id);
}
