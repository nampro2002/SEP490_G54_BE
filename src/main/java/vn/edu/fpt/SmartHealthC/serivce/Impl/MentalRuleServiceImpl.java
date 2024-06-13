package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRuleRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRuleResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Lesson;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.serivce.MentalRuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MentalRuleServiceImpl implements MentalRuleService {

    @Autowired
    private MentalRuleRepository mentalRuleRepository;

    @Override
    public MentalRuleResponseDTO createMentalRule(MentalRuleRequestDTO mentalRuleRequestDTO) {
        MentalRule mentalRule = MentalRule
                .builder()
                .title(mentalRuleRequestDTO.getTitle())
                .description(mentalRuleRequestDTO.getDescription())
                .isDeleted(false)
                .build();
        mentalRule = mentalRuleRepository.save(mentalRule);
        return MentalRuleResponseDTO
                .builder()
                .id(mentalRule.getId())
                .title(mentalRule.getTitle())
                .description(mentalRule.getDescription())
                .isDeleted(mentalRule.isDeleted())
                .build();
    }

    @Override
    public MentalRule getMentalRuleEntityById(Integer id) {
        Optional<MentalRule> mentalRule = mentalRuleRepository.findById(id);
        if(mentalRule.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }
        return mentalRule.get();
    }


    @Override
    public MentalRuleResponseDTO getMentalRuleById(Integer id) {
        Optional<MentalRule> mentalRule = mentalRuleRepository.findById(id);
        if(mentalRule.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }
        return MentalRuleResponseDTO
                .builder()
                .id(mentalRule.get().getId())
                .title(mentalRule.get().getTitle())
                .description(mentalRule.get().getDescription())
                .isDeleted(mentalRule.get().isDeleted())
                .build();
    }
    @Override
    public List<MentalRuleResponseDTO> getAllMentalRules(Integer pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MentalRule> pagedResult = mentalRuleRepository.findAll(paging);
        List<MentalRule> mentalRuleList= new ArrayList<>();
        if (pagedResult.hasContent()) {
            mentalRuleList = pagedResult.getContent();
        }
        List<MentalRuleResponseDTO> mentalRuleResponseDTOList = new ArrayList<>();
        for (MentalRule mentalRule : mentalRuleList) {
            MentalRuleResponseDTO mentalRuleResponseDTO = MentalRuleResponseDTO
                    .builder()
                    .id(mentalRule.getId())
                    .title(mentalRule.getTitle())
                    .description(mentalRule.getDescription())
                    .isDeleted(mentalRule.isDeleted())
                    .build();
            mentalRuleResponseDTOList.add(mentalRuleResponseDTO);
        }
        return mentalRuleResponseDTOList.stream().filter(record -> record.getTitle().toLowerCase().contains(search.toLowerCase())).toList();
    }

    @Override
    public MentalRuleResponseDTO updateMentalRule(Integer id, MentalRuleRequestDTO mentalRuleRequestDTO) {
        MentalRule mentalRule = getMentalRuleEntityById(id);
        mentalRule.setDeleted(mentalRuleRequestDTO.isDeleted());
        mentalRule.setDescription(mentalRuleRequestDTO.getDescription());
        mentalRule.setTitle(mentalRuleRequestDTO.getTitle());

        mentalRule =  mentalRuleRepository.save(mentalRule);
        return MentalRuleResponseDTO
                .builder()
                .id(mentalRule.getId())
                .title(mentalRule.getTitle())
                .description(mentalRule.getDescription())
                .isDeleted(mentalRule.isDeleted())
                .build();
    }

    @Override
    public MentalRuleResponseDTO deleteMentalRule(Integer id) {
        MentalRule mentalRule = getMentalRuleEntityById(id);
        mentalRuleRepository.deleteById(id);
        return MentalRuleResponseDTO
                .builder()
                .id(mentalRule.getId())
                .title(mentalRule.getTitle())
                .description(mentalRule.getDescription())
                .isDeleted(mentalRule.isDeleted())
                .build();
    }
}
