package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserUpdateRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AccountResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.dto.response.WebUserResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/web-users")
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    // create at account controller
//    @PostMapping
//    public ResponseEntity<WebUser> createWebUser(@RequestBody WebUser webUser) {
//        WebUser createdWebUser = webUserService.createWebUser(webUser);
//        return ResponseEntity.ok(createdWebUser);
//    }

    @GetMapping("/{id}")
    public ApiResponse<WebUser> getWebUserById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WebUser>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.getWebUserById(id))
                        .build()).getBody();
    }
    @GetMapping("/detail")
    public ApiResponse<WebUserResponseDTO> getDetailCurrentWebUser() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WebUserResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.getDetailCurrentWebUser())
                        .build()).getBody();
    }


    @GetMapping
    public ApiResponse<List<WebUser>> getAllWebUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<WebUser>>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.getAllWebUsers())
                        .build()).getBody();
    }

    @PutMapping()
    public ApiResponse<WebUserResponseDTO> updateWebUser(@RequestBody @Valid WebUserUpdateRequestDTO webUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WebUserResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.updateWebUser(webUserRequestDTO))
                        .build()).getBody();
    }

    @GetMapping("/get-list-doctor")
    public ApiResponse<ResponsePaging<List<WebUserResponseDTO>>> getListDoctorNotDelete(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<WebUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.getListDoctorNotDelete(pageNo-1, search))
                        .build()).getBody();
    }
    @GetMapping("/get-list-ms-admin")
    public ApiResponse<ResponsePaging<List<WebUserResponseDTO>>> getListMsAdminNotDelete(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<WebUserResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.getListMsAdminNotDelete(pageNo-1, search))
                        .build()).getBody();
    }

// delete  at account controller
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteWebUser(@PathVariable Integer id) {
//        webUserService.deleteWebUser(id);
//        return ResponseEntity.noContent().build();
//    }
}