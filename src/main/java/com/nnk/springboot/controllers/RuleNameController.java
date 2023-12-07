package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RuleNameController {

    private final RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        List<RuleName> ruleNames = ruleNameService.findAll();
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.create(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> optionalRuleName = ruleNameService.findById(id);
        if (optionalRuleName.isEmpty()) {
            return "redirect:/ruleName/list";
        }
        model.addAttribute("ruleName", optionalRuleName.get());
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleNameService.findById(id)
                .ifPresent(existingRuleName -> ruleNameService.update(existingRuleName, ruleName));
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
