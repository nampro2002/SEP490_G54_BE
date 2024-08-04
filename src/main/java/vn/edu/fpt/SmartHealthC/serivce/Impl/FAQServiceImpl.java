package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FAQRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FAQResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.FAQ;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FAQRepository;
import vn.edu.fpt.SmartHealthC.serivce.FAQService;

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
                .questionEn(faqRequestDTO.getQuestionEn())
                .answer(faqRequestDTO.getAnswer())
                .answerEn(faqRequestDTO.getAnswerEn())
                .build();
        faq = faqRepository.save(faq);
        FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .questionEn(faq.getQuestionEn())
                .answer(faq.getAnswer())
                .answerEn(faq.getAnswerEn())
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
                .questionEn(faq.get().getQuestionEn())
                .answer(faq.get().getAnswer())
                .answerEn(faq.get().getAnswerEn())
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
                    .questionEn(faq.getQuestionEn())
                    .answer(faq.getAnswer())
                    .answerEn(faq.getAnswerEn())
                    .build();
            responseDTOList.add(faqResponseDTO);
        }
        return responseDTOList;
    }

    @Override
    public FAQResponseDTO updateFAQ(Integer id, FAQRequestDTO faq) {
        FAQ faqEntity = getFAQEntityById(id);
        faqEntity.setQuestion(faq.getQuestion());
        faqEntity.setQuestionEn(faq.getQuestionEn());
        faqEntity.setAnswer(faq.getAnswer());
        faqEntity.setAnswerEn(faq.getAnswerEn());
        faqEntity = faqRepository.save(faqEntity);
        FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                .id(faqEntity.getId())
                .question(faqEntity.getQuestion())
                .questionEn(faqEntity.getQuestionEn())
                .answer(faqEntity.getAnswer())
                .answerEn(faqEntity.getAnswerEn())
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
                .questionEn(faqEntity.getQuestionEn())
                .answer(faqEntity.getAnswer())
                .answerEn(faqEntity.getAnswerEn())
                .build();
        return faqResponseDTO;
    }

    @Override
    public List<FAQResponseDTO> getAllFAQsMobile() {
        List<FAQResponseDTO> responseDTOList = new ArrayList<>();
        List<FAQ> faqList = faqRepository.findAll();
        for (FAQ faq : faqList) {
            FAQResponseDTO faqResponseDTO = FAQResponseDTO.builder()
                    .id(faq.getId())
                    .question(faq.getQuestion())
                    .questionEn(faq.getQuestionEn())
                    .answer(faq.getAnswer())
                    .answerEn(faq.getAnswerEn())
                    .build();
            responseDTOList.add(faqResponseDTO);
        }
        return responseDTOList;
    }

    @Override
    public ResponsePaging<List<FAQResponseDTO>> getAllFAQsPaging(int pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<FAQ> pagedResult  = faqRepository.findAll(paging, search.toLowerCase());
        List<FAQ> faqList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            faqList = pagedResult.getContent();
        }
        List<FAQResponseDTO> formQuestionResponseDTOS = new ArrayList<>();
        for (FAQ formQuestion : faqList) {
            FAQResponseDTO faqResponseDTO = FAQResponseDTO
                    .builder()
                    .id(formQuestion.getId())
                    .questionEn(formQuestion.getQuestionEn())
                    .question(formQuestion.getQuestion())
                    .answer(formQuestion.getAnswer())
                    .answerEn(formQuestion.getAnswerEn())
                    .build();
            formQuestionResponseDTOS.add(faqResponseDTO);
        }
        return ResponsePaging.<List<FAQResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(formQuestionResponseDTOS)
                .build();
    }
}
