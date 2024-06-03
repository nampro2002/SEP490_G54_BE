package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface FAQService {
    FAQ createFAQ(FAQ faq);
    Optional<FAQ> getFAQById(Integer id);
    List<FAQ> getAllFAQs();
    FAQ updateFAQ(FAQ faq);
    void deleteFAQ(Integer id);
}
