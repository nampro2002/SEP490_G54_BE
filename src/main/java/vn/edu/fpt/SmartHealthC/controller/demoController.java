package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xx/demo")
public class demoController {

    @GetMapping
    public ResponseEntity<String> authed(){
        return ResponseEntity.ok("You logged in!");
    }

    @GetMapping("/data")
    public String getData() {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            return "Admin specific data";
        } else {
            return "User specific data";
        }
    }
}
