package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.FormQuestionRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.FormQuestion;

import java.util.List;

public interface FormQuestionService {
    FormQuestionResponseDTO createFormQuestion(FormQuestionRequestDTO formQuestion);
    FormQuestionResponseDTO  getFormQuestionById(Integer id);
    FormQuestion  getFormQuestionEntityById(Integer id);
    ResponsePaging<List<FormQuestionResponseDTO>> getAllFormQuestions(Integer pageNo, String search);
    FormQuestionResponseDTO updateFormQuestion(Integer id,FormQuestionRequestDTO formQuestion);
    FormQuestionResponseDTO deleteFormQuestion(Integer id);

    List<FormQuestionResponseDTO> getAllFormQuestionsMobile();
}
