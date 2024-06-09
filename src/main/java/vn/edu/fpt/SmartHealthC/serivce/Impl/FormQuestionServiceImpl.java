package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.FormQuestionRepository;
import vn.edu.fpt.SmartHealthC.repository.LessonRepository;
import vn.edu.fpt.SmartHealthC.serivce.FormQuestionService;
import vn.edu.fpt.SmartHealthC.serivce.LessonService;

import java.util.List;
import java.util.Optional;

@Service
public class FormQuestionServiceImpl implements FormQuestionService {
    @Autowired
    private FormQuestionRepository formQuestionRepository;

    @Override
    public FormQuestion createFormQuestion(FormQuestionRequestDTO formQuestionRequestDTO) {
        FormQuestion formQuestion = FormQuestion
                .builder()
                .question(formQuestionRequestDTO.getQuestion())
                .questionNumber(formQuestionRequestDTO.getQuestionNumber())
                .type(formQuestionRequestDTO.getType())
                .build();
        return formQuestionRepository.save(formQuestion);
    }

    @Override
    public FormQuestion getFormQuestionById(Integer id) {
        Optional<FormQuestion> formQuestion = formQuestionRepository.findById(id);
        if (!formQuestion.isPresent()) {
            throw new AppException(ErrorCode.FORM_QUESTION_NOT_FOUND);
        }
        return formQuestion.get();
    }

    @Override
    public List<FormQuestion> getAllFormQuestions() {
        return formQuestionRepository.findAll();
    }

    @Override
    public FormQuestion updateFormQuestion(FormQuestionRequestDTO formQuestionRequestDTO) {
        FormQuestion formQuestion = getFormQuestionById(formQuestionRequestDTO.getId());
        formQuestion.setQuestion(formQuestionRequestDTO.getQuestion());
        formQuestion.setQuestionNumber(formQuestionRequestDTO.getQuestionNumber());
        formQuestion.setType(formQuestionRequestDTO.getType());
        return formQuestionRepository.save(formQuestion);
    }

    @Override
    public FormQuestion deleteFormQuestion(Integer id) {
        FormQuestion formQuestion = getFormQuestionById(id);
        formQuestionRepository.deleteById(id);
        return formQuestion;
    }
}
