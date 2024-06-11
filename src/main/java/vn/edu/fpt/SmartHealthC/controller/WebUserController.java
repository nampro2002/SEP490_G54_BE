package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
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

    @GetMapping
    public ApiResponse<List<WebUser>> getAllWebUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<WebUser>>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.getAllWebUsers())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<WebUser> updateWebUser(@PathVariable Integer id, @RequestBody WebUserRequestDTO webUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<WebUser>builder()
                        .code(HttpStatus.OK.value())
                        .result(webUserService.updateWebUser(webUserRequestDTO, id))
                        .build()).getBody();
    }



// delete  at account controller
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteWebUser(@PathVariable Integer id) {
//        webUserService.deleteWebUser(id);
//        return ResponseEntity.noContent().build();
//    }
}