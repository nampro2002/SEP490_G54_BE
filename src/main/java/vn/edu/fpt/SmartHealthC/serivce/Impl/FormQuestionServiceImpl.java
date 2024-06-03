package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
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
    public FormQuestion createFormQuestion(FormQuestion formQuestion) {
        return formQuestionRepository.save(formQuestion);
    }

    @Override
    public Optional<FormQuestion> getFormQuestionById(Integer id) {
        return formQuestionRepository.findById(id);
    }

    @Override
    public List<FormQuestion> getAllFormQuestions() {
        return formQuestionRepository.findAll();
    }

    @Override
    public FormQuestion updateFormQuestion(FormQuestion formQuestion) {
        return formQuestionRepository.save(formQuestion);
    }

    @Override
    public void deleteFormQuestion(Integer id) {
        formQuestionRepository.deleteById(id);
    }
}
