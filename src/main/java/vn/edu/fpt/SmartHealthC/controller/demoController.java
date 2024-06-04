package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class demoController {

    @GetMapping
    public ResponseEntity<String> authed(){
        return ResponseEntity.ok("You logged in!");
    }

}
