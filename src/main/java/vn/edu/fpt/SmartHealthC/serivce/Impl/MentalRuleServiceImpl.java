package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.serivce.MentalRuleService;

import java.util.List;
import java.util.Optional;
@Service
public class MentalRuleServiceImpl implements MentalRuleService {

    @Autowired
    private MentalRuleRepository mentalRuleRepository;

    @Override
    public MentalRule createMentalRule(MentalRule mentalRule) {
        return mentalRuleRepository.save(mentalRule);
    }

    @Override
    public MentalRule getMentalRuleById(Integer id) {
        Optional<MentalRule> mentalRule = mentalRuleRepository.findById(id);
        if(mentalRule.isEmpty()) {
            throw new AppException(ErrorCode.MENTAL_RULE_NOT_FOUND);
        }
        return mentalRule.get();
    }

    @Override
    public List<MentalRule> getAllMentalRules() {
        return mentalRuleRepository.findAll();
    }

    @Override
    public MentalRule updateMentalRule(Integer id,MentalRule mentalRule) {
        MentalRule updatedMentalRule = getMentalRuleById(id);
        updatedMentalRule.setDescription(mentalRule.getDescription());
        updatedMentalRule.setTitle(mentalRule.getTitle());
        return mentalRuleRepository.save(mentalRule);
    }

    @Override
    public MentalRule deleteMentalRule(Integer id) {
        MentalRule mentalRule = getMentalRuleById(id);
        mentalRuleRepository.deleteById(id);
        return mentalRule;
    }
}
