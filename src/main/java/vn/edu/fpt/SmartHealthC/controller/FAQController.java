package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<FAQ> createFAQ(@RequestBody FAQ faq) {
        FAQ createdFaq = faqService.createFAQ(faq);
        return ResponseEntity.ok(createdFaq);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQ> getFAQById(@PathVariable Integer id) {
        Optional<FAQ> faqById = faqService.getFAQById(id);
        return faqById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FAQ>> getAllFAQs() {
        List<FAQ> faqs = faqService.getAllFAQs();
        return ResponseEntity.ok(faqs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FAQ> updateFAQ(@PathVariable Integer id, @RequestBody FAQ faq) {
        faq.setId(id);
        FAQ updatedFAQ = faqService.updateFAQ(faq);
        return ResponseEntity.ok(updatedFAQ);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Integer id) {
        faqService.deleteFAQ(id);
        return ResponseEntity.noContent().build();
    }
}
