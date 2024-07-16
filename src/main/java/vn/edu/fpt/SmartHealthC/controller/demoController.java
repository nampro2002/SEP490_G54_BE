package vn.edu.fpt.SmartHealthC.controller;

import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/xx/demo")
public class demoController {
    @Autowired
    private MedicineRecordRepository medicineRecordRepository;

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
    @PostMapping("/time")
    public String getTimebytime() throws ParseException {
        Date date = DateUtils.getTime();
        List<MedicineRecord> stepRecord = medicineRecordRepository.findByTime(date);

        return "ok";
    }

}
