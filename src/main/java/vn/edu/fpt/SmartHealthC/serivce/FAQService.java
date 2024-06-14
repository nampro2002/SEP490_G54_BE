package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FAQResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface FAQService {
    FAQResponseDTO createFAQ(FAQRequestDTO faq);
    FAQ getFAQEntityById(Integer id);
    FAQResponseDTO getFAQById(Integer id);
    List<FAQResponseDTO> getAllFAQs();
    FAQResponseDTO updateFAQ(Integer id,FAQRequestDTO faq);
    FAQResponseDTO deleteFAQ(Integer id);
}
