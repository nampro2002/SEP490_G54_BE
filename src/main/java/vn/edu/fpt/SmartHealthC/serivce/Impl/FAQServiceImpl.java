package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
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
    public FAQ createFAQ(FAQ faq) {
        return faqRepository.save(faq);
    }

    @Override
    public Optional<FAQ> getFAQById(Integer id) {
        return  faqRepository.findById(id);
    }

    @Override
    public List<FAQ> getAllFAQs() {
        return faqRepository.findAll();
    }

    @Override
    public FAQ updateFAQ(FAQ faq) {
        return faqRepository.save(faq);
    }

    @Override
    public void deleteFAQ(Integer id) {
        faqRepository.deleteById(id);
    }
}
