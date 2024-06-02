package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;
import vn.edu.fpt.SmartHealthC.repository.MentalRuleRepository;
import vn.edu.fpt.SmartHealthC.serivce.MentalRuleService;

import java.util.List;
import java.util.Optional;

public class MentalRuleServiceImpl implements MentalRuleService {

    @Autowired
    private MentalRuleRepository mentalRuleRepository;

    @Override
    public MentalRule createMentalRule(MentalRule mentalRule) {
        return mentalRuleRepository.save(mentalRule);
    }

    @Override
    public Optional<MentalRule> getMentalRuleById(Integer id) {
        return mentalRuleRepository.findById(id);
    }

    @Override
    public List<MentalRule> getAllMentalRules() {
        return mentalRuleRepository.findAll();
    }

    @Override
    public MentalRule updateMentalRule(MentalRule mentalRule) {
        return mentalRuleRepository.save(mentalRule);
    }

    @Override
    public void deleteMentalRule(Integer id) {
        mentalRuleRepository.deleteById(id);
    }
}
