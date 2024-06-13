package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FAQResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FAQRepository;
import vn.edu.fpt.SmartHealthC.repository.LessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.FAQService;
import vn.edu.fpt.SmartHealthC.serivce.LessonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepository faqRepository;


    @Override
    public FAQResponseDTO createFAQ(FAQRequestDTO faqRequestDTO) {
        FAQ faq = FAQ.builder()
                .question(faqRequestDTO.getQuestion())
                .answer(faqRequestDTO.getAnswer())
                .build();
        faq = faqRepository.save(faq);
        FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
        return faqResponseDTO;
    }

    @Override
    public FAQResponseDTO getFAQById(Integer id) {
        Optional<FAQ> faq = faqRepository.findById(id);
        if (!faq.isPresent()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                .id(faq.get().getId())
                .question(faq.get().getQuestion())
                .answer(faq.get().getAnswer())
                .build();
        return faqResponseDTO;
    }

    @Override
    public FAQ getFAQEntityById(Integer id) {
        Optional<FAQ> faq = faqRepository.findById(id);
        if (!faq.isPresent()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return faq.get();
    }

    @Override
    public List<FAQResponseDTO> getAllFAQs() {
        List<FAQResponseDTO> responseDTOList = new ArrayList<>();
        List<FAQ> faqList = faqRepository.findAll();
        for (FAQ faq : faqList) {
            FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                    .id(faq.getId())
                    .question(faq.getQuestion())
                    .answer(faq.getAnswer())
                    .build();
            responseDTOList.add(faqResponseDTO);
        }
        return responseDTOList;
    }

    @Override
    public FAQResponseDTO updateFAQ(Integer id, FAQRequestDTO faq) {
        FAQ faqEntity = getFAQEntityById(id);
        faqEntity.setQuestion(faq.getQuestion());
        faqEntity.setAnswer(faq.getAnswer());
        faqEntity = faqRepository.save(faqEntity);
        FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                .id(faqEntity.getId())
                .question(faqEntity.getQuestion())
                .answer(faqEntity.getAnswer())
                .build();
        return faqResponseDTO;
    }

    @Override
    public FAQResponseDTO deleteFAQ(Integer id) {
        FAQ faqEntity = getFAQEntityById(id);
        faqRepository.deleteById(id);
        FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                .id(faqEntity.getId())
                .question(faqEntity.getQuestion())
                .answer(faqEntity.getAnswer())
                .build();
        return faqResponseDTO;
    }
}
