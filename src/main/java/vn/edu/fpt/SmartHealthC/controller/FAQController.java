package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AuthenticationResponseDto;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FAQResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.serivce.FAQService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/faq")
public class FAQController {
    @Autowired
    private FAQService faqService;

    @PostMapping
    public ApiResponse<FAQResponseDTO> createFAQ(@RequestBody @Valid FAQRequestDTO faqRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(faqService.createFAQ(faqRequestDTO))
                        .build()).getBody();
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getFAQById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .result(faqService.getFAQById(id))
                        .build()).getBody();
    }

    @GetMapping("/get-all")
    public ApiResponse<?> getAllFAQs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FAQResponseDTO>>builder()
                        .result(faqService.getAllFAQs())
                        .build()).getBody();
    }
    @GetMapping("/mobile")
    public ApiResponse<?> getAllFAQsMobile() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FAQResponseDTO>>builder()
                        .result(faqService.getAllFAQsMobile())
                        .build()).getBody();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateFAQ(@PathVariable Integer id, @RequestBody @Valid FAQRequestDTO faq) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(faqService.updateFAQ(id,faq))
                        .build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteFAQ(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .result(faqService.deleteFAQ(id))
                        .build()).getBody();
    }
}
