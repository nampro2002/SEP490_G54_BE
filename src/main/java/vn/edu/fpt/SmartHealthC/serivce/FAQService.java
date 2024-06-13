package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface FAQService {
    FAQ createFAQ(FAQRequestDTO faq);
    FAQ getFAQById(Integer id);
    List<FAQ> getAllFAQs();
    FAQ updateFAQ(Integer id,FAQRequestDTO faq);
    FAQ deleteFAQ(Integer id);
}
