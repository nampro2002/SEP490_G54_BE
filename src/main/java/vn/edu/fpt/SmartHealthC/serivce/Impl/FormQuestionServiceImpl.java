package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResDTO.FormMonthlyQuestionDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResDTO.FormMonthlyResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FormQuestionRepository;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormQuestionServiceImpl implements FormQuestionService {
    @Autowired
    private FormQuestionRepository formQuestionRepository;

    @Override
    public FormQuestionResponseDTO createFormQuestion(FormQuestionRequestDTO formQuestionRequestDTO) {
            Optional<FormQuestion> formQuestionDuplicate = formQuestionRepository.findRecordByQuestionNumberAndType(formQuestionRequestDTO.getQuestionNumber(), formQuestionRequestDTO.getType());
        if(formQuestionDuplicate.isPresent()){
            throw new AppException(ErrorCode.FORM_QUESTION_NUMBER_DUPLICATE);
        }
        FormQuestion formQuestion = FormQuestion
                .builder()
                .question(formQuestionRequestDTO.getQuestion())
                .questionEn(formQuestionRequestDTO.getQuestionEn())
                .questionNumber(formQuestionRequestDTO.getQuestionNumber())
                .type(formQuestionRequestDTO.getType())
                .build();
        formQuestion = formQuestionRepository.save(formQuestion);
        FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                .builder()
                .id(formQuestion.getId())
                .question(formQuestion.getQuestion())
                .questionEn(formQuestion.getQuestionEn())
                .questionNumber(formQuestion.getQuestionNumber())
                .type(formQuestion.getType())
                .build();
        return formQuestionResponseDTO;
    }

    @Override
    public FormQuestion getFormQuestionEntityById(Integer id) {
        Optional<FormQuestion> formQuestion = formQuestionRepository.findById(id);
        if (!formQuestion.isPresent()) {
            throw new AppException(ErrorCode.FORM_QUESTION_NOT_FOUND);
        }
        return formQuestion.get();
    }

    @Override
    public FormQuestionResponseDTO getFormQuestionById(Integer id) {
        Optional<FormQuestion> formQuestion = formQuestionRepository.findById(id);
        if (!formQuestion.isPresent()) {
            throw new AppException(ErrorCode.FORM_QUESTION_NOT_FOUND);
        }
        FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                .builder()
                .id(formQuestion.get().getId())
                .question(formQuestion.get().getQuestion())
                .questionEn(formQuestion.get().getQuestionEn())
                .questionNumber(formQuestion.get().getQuestionNumber())
                .type(formQuestion.get().getType())
                .build();
        return formQuestionResponseDTO;
    }

    @Override
    public ResponsePaging<List<FormQuestionResponseDTO>> getAllFormQuestions(Integer pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<FormQuestion> pagedResult  = formQuestionRepository.findAll(paging, search.toLowerCase());
        List<FormQuestion> formQuestions = new ArrayList<>();
        if (pagedResult.hasContent()) {
            formQuestions = pagedResult.getContent();
        }
        List<FormQuestionResponseDTO> formQuestionResponseDTOS = new ArrayList<>();
        for (FormQuestion formQuestion : formQuestions) {
            FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                    .builder()
                    .id(formQuestion.getId())
                    .question(formQuestion.getQuestion())
                    .questionEn(formQuestion.getQuestionEn())
                    .questionNumber(formQuestion.getQuestionNumber())
                    .type(formQuestion.getType())
                    .build();
            formQuestionResponseDTOS.add(formQuestionResponseDTO);
        }
        return ResponsePaging.<List<FormQuestionResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(formQuestionResponseDTOS)
                .build();
    }

    @Override
    public FormQuestionResponseDTO updateFormQuestion(Integer id,FormQuestionRequestDTO formQuestionRequestDTO) {
        FormQuestion formQuestion = getFormQuestionEntityById(id);
        formQuestion.setQuestion(formQuestionRequestDTO.getQuestion());
        formQuestion.setQuestionEn(formQuestionRequestDTO.getQuestionEn());
//        formQuestion.setQuestionNumber(formQuestionRequestDTO.getQuestionNumber());
        formQuestion.setType(formQuestionRequestDTO.getType());
        formQuestion = formQuestionRepository.save(formQuestion);
        FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                .builder()
                .id(formQuestion.getId())
                .question(formQuestion.getQuestion())
                .questionEn(formQuestion.getQuestionEn())
                .questionNumber(formQuestion.getQuestionNumber())
                .type(formQuestion.getType())
                .build();
        return formQuestionResponseDTO;
    }

    @Override
    public FormQuestionResponseDTO deleteFormQuestion(Integer id) {
        FormQuestion formQuestion = getFormQuestionEntityById(id);
        formQuestionRepository.deleteById(id);
        FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                .builder()
                .id(formQuestion.getId())
                .question(formQuestion.getQuestion())
                .questionEn(formQuestion.getQuestionEn())
                .questionNumber(formQuestion.getQuestionNumber())
                .type(formQuestion.getType())
                .build();
        return formQuestionResponseDTO;
    }

    @Override
    public List<FormQuestionResponseDTO> getAllFormQuestionsMobile(TypeLanguage language) {
        List<FormQuestion> formQuestions   = formQuestionRepository.findAll();
        List<FormQuestionResponseDTO> formQuestionResponseDTOS = new ArrayList<>();
        for (FormQuestion formQuestion : formQuestions) {
            FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                    .builder()
                    .id(formQuestion.getId())
                    .question(language != TypeLanguage.EN ? formQuestion.getQuestion() : formQuestion.getQuestionEn())
                    .questionEn(formQuestion.getQuestionEn())
                    .questionNumber(formQuestion.getQuestionNumber())
                    .type(formQuestion.getType())
                    .build();
            formQuestionResponseDTOS.add(formQuestionResponseDTO);
        }
        return formQuestionResponseDTOS;
    }

    @Override
    public FormMonthlyResponseDTO getFormMonthlyMobile(MonthlyRecordType type,TypeLanguage language) {
        List<FormQuestion> formQuestionList = formQuestionRepository.findRecordByType(type);
            List<FormMonthlyQuestionDTO> formMonthlyResponseDTOS = new ArrayList<>();
            for (FormQuestion formQuestion : formQuestionList) {
                FormMonthlyQuestionDTO formMonthlyResponseDTO = FormMonthlyQuestionDTO
                        .builder()
                        .question(language == TypeLanguage.EN ? formQuestion.getQuestionEn() : formQuestion.getQuestion())
                        .questionEn(formQuestion.getQuestionEn())
                        .questionNumber(formQuestion.getQuestionNumber())
                        .build();
                formMonthlyResponseDTOS.add(formMonthlyResponseDTO);
            }
        FormMonthlyResponseDTO formMonthlyResponseDTO = FormMonthlyResponseDTO
                .builder()
                .type(type)
                .formMonthlyQuestionDTOList(formMonthlyResponseDTOS)
                .build();
        return formMonthlyResponseDTO;
    }

}
