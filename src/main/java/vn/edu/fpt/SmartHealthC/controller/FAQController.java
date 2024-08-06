package vn.edu.fpt.SmartHealthC.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.serivce.FAQService;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/faq")
public class FAQController {
    @Autowired
    private FAQService faqService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<FAQResponseDTO> createFAQ(@RequestBody @Valid FAQRequestDTO faqRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .code(HttpStatus.CREATED.value())
                        .result(faqService.createFAQ(faqRequestDTO))
                        .build()).getBody();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<?> getFAQById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .result(faqService.getFAQById(id))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/web/others")
    public ApiResponse<ResponsePaging<List<FAQResponseDTO>>> getAllFormQuestions(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ResponsePaging<List<FAQResponseDTO>>>builder()
                        .code(HttpStatus.OK.value())
                        .result(faqService.getAllFAQsPaging(pageNo-1, search))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<?> getAllFAQs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FAQResponseDTO>>builder()
                        .result(faqService.getAllFAQs())
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/mobile/{language}")
    public ApiResponse<?> getAllFAQsMobile(@PathVariable TypeLanguage language) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FAQResponseDTO>>builder()
                        .result(faqService.getAllFAQsMobile(language))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<?> updateFAQ(@PathVariable Integer id, @RequestBody @Valid FAQRequestDTO faq) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .result(faqService.updateFAQ(id,faq))
                        .build()).getBody();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteFAQ(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FAQResponseDTO>builder()
                        .result(faqService.deleteFAQ(id))
                        .build()).getBody();
    }
}
