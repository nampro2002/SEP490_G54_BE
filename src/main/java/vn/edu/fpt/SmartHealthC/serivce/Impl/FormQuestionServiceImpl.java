package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FormQuestionRepository;
import vn.edu.fpt.SmartHealthC.repository.LessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;
import vn.edu.fpt.SmartHealthC.serivce.LessonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormQuestionServiceImpl implements FormQuestionService {
    @Autowired
    private FormQuestionRepository formQuestionRepository;

    @Override
    public FormQuestionResponseDTO createFormQuestion(FormQuestionRequestDTO formQuestionRequestDTO) {
        FormQuestion formQuestion = FormQuestion
                .builder()
                .question(formQuestionRequestDTO.getQuestion())
                .questionNumber(formQuestionRequestDTO.getQuestionNumber())
                .type(formQuestionRequestDTO.getType())
                .build();
        formQuestion = formQuestionRepository.save(formQuestion);
        FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                .builder()
                .id(formQuestion.getId())
                .question(formQuestion.getQuestion())
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
                .questionNumber(formQuestion.get().getQuestionNumber())
                .type(formQuestion.get().getType())
                .build();
        return formQuestionResponseDTO;
    }

    @Override
    public List<FormQuestionResponseDTO> getAllFormQuestions() {
        List<FormQuestion> formQuestions = formQuestionRepository.findAll();
        List<FormQuestionResponseDTO> formQuestionResponseDTOS = new ArrayList<>();
        for (FormQuestion formQuestion : formQuestions) {
            FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                    .builder()
                    .id(formQuestion.getId())
                    .question(formQuestion.getQuestion())
                    .questionNumber(formQuestion.getQuestionNumber())
                    .type(formQuestion.getType())
                    .build();
            formQuestionResponseDTOS.add(formQuestionResponseDTO);
        }
        return formQuestionResponseDTOS;
    }

    @Override
    public FormQuestionResponseDTO updateFormQuestion(Integer id,FormQuestionRequestDTO formQuestionRequestDTO) {
        FormQuestion formQuestion = getFormQuestionEntityById(id);
        formQuestion.setQuestion(formQuestionRequestDTO.getQuestion());
        formQuestion.setQuestionNumber(formQuestionRequestDTO.getQuestionNumber());
        formQuestion.setType(formQuestionRequestDTO.getType());
        formQuestion = formQuestionRepository.save(formQuestion);
        FormQuestionResponseDTO formQuestionResponseDTO = FormQuestionResponseDTO
                .builder()
                .id(formQuestion.getId())
                .question(formQuestion.getQuestion())
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
                .questionNumber(formQuestion.getQuestionNumber())
                .type(formQuestion.getType())
                .build();
        return formQuestionResponseDTO;
    }
}
