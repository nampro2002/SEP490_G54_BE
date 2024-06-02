package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/web-users")
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    @PostMapping
    public ResponseEntity<WebUser> createWebUser(@RequestBody WebUser webUser) {
        WebUser createdWebUser = webUserService.createWebUser(webUser);
        return ResponseEntity.ok(createdWebUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebUser> getWebUserById(@PathVariable Integer id) {
        Optional<WebUser> webUser = webUserService.getWebUserById(id);
        return webUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<WebUser>> getAllWebUsers() {
        List<WebUser> webUsers = webUserService.getAllWebUsers();
        return ResponseEntity.ok(webUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebUser> updateWebUser(@PathVariable Integer id, @RequestBody WebUser webUser) {
        WebUser updatedWebUser = webUserService.updateWebUser(webUser, id);
        return ResponseEntity.ok(updatedWebUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebUser(@PathVariable Integer id) {
        webUserService.deleteWebUser(id);
        return ResponseEntity.noContent().build();
    }
}