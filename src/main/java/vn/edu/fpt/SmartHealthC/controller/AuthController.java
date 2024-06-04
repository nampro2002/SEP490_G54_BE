//package vn.edu.fpt.SmartHealthC.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import vn.edu.fpt.SmartHealthC.domain.dto.AuthenticationResponseDto;
//import vn.edu.fpt.SmartHealthC.domain.dto.LoginDto;
//import vn.edu.fpt.SmartHealthC.domain.dto.RegisterDto;
//import vn.edu.fpt.SmartHealthC.domain.entity.Account;
//import vn.edu.fpt.SmartHealthC.repository.AccountRepository;
//import vn.edu.fpt.SmartHealthC.serivce.AuthService;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService service;
//    private final AccountRepository repo;
//    @GetMapping("/demo")
//    public String demoAccess() {
//        return "Connection Established";
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponseDto> register(
//            @RequestBody RegisterDto request) {
//        return ResponseEntity.ok(service.register(request));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponseDto> register(
//            @RequestBody LoginDto request) {
//        return ResponseEntity.ok(service.login(request));
//    }
//
//    @GetMapping("/accounts")
//    public ResponseEntity<List<Account>> getAccountList(){
//        return ResponseEntity.ok(repo.findAll());
//    }
//}
