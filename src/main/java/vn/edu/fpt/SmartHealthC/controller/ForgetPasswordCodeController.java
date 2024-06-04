package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.ForgetPasswordCodeDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ForgetPasswordCode;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.serivce.ForgetPasswordCodeService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forget-password")
public class ForgetPasswordCodeController {

    @Autowired
    private ForgetPasswordCodeService forgetPasswordCodeService;

    @PostMapping
    public ResponseEntity<ForgetPasswordCode> createForgetPasswordCode(@RequestBody ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
        ForgetPasswordCode createdForgetPasswordCode= forgetPasswordCodeService.createForgetPasswordCode(forgetPasswordCodeDTO);
        return ResponseEntity.ok(createdForgetPasswordCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForgetPasswordCode> getForgetPasswordCodeById(@PathVariable Integer id) {
        Optional<ForgetPasswordCode> forgetPasswordCode = forgetPasswordCodeService.getForgetPasswordCodeById(id);
        return forgetPasswordCode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ForgetPasswordCode>> getAllForgetPasswordCodes() {
        List<ForgetPasswordCode> forgetPasswordCodes = forgetPasswordCodeService.getAllForgetPasswordCodes();
        return ResponseEntity.ok(forgetPasswordCodes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForgetPasswordCode> updateForgetPasswordCode(@PathVariable Integer id, @RequestBody ForgetPasswordCodeDTO forgetPasswordCodeDTO) {
        forgetPasswordCodeDTO.setId(id);
        ForgetPasswordCode updatedForgetPasswordCode = forgetPasswordCodeService.updateForgetPasswordCode(forgetPasswordCodeDTO);
        return ResponseEntity.ok(updatedForgetPasswordCode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForgetPasswordCode(@PathVariable Integer id) {
        forgetPasswordCodeService.deleteForgetPasswordCode(id);
        return ResponseEntity.noContent().build();
    }
}