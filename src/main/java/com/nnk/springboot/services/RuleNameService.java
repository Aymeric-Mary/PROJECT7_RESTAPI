package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;
    private final RuleNameMapper ruleNameMapper;

    public RuleName create(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public RuleName update(RuleName existingRuleName, RuleName ruleName) {
        ruleNameMapper.updateRuleName(existingRuleName, ruleName);
        return ruleNameRepository.save(existingRuleName);
    }

    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
