package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FAQRepository;
import vn.edu.fpt.SmartHealthC.repository.LessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.FAQService;
import vn.edu.fpt.SmartHealthC.serivce.LessonService;

import java.util.List;
import java.util.Optional;

@Service
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepository faqRepository;


    @Override
    public FAQ createFAQ(FAQRequestDTO faqRequestDTO) {
        FAQ faq = FAQ.builder()
                .question(faqRequestDTO.getQuestion())
                .answer(faqRequestDTO.getAnswer())
                .build();
        return faqRepository.save(faq);
    }

    @Override
    public FAQ getFAQById(Integer id) {
        Optional<FAQ> faq = faqRepository.findById(id);
        if (!faq.isPresent()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return faq.get();
    }

    @Override
    public List<FAQ> getAllFAQs() {
        return faqRepository.findAll();
    }

    @Override
    public FAQ updateFAQ(FAQRequestDTO faq) {
        FAQ faqEntity = getFAQById(faq.getId());
        faqEntity.setQuestion(faq.getQuestion());
        faqEntity.setAnswer(faq.getAnswer());
        return faqRepository.save(faqEntity);
    }

    @Override
    public FAQ deleteFAQ(Integer id) {
        FAQ faqEntity = getFAQById(id);
        faqRepository.deleteById(id);
        return faqEntity;
    }
}
