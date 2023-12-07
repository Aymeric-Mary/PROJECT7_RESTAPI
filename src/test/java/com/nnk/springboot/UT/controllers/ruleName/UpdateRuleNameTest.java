package com.nnk.springboot.UT.controllers.ruleName;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRuleNameTest {

    @Mock
    RuleNameService ruleNameServiceMock;

    @Mock
    BindingResult bindingResultMock;

    @InjectMocks
    RuleNameController ruleNameController;

    @Test
    void testUpdateRuleNameWhenHasError() {
        // Given
        RuleName ruleName = new RuleName();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = ruleNameController.updateRuleName(1234, ruleName, bindingResultMock);
        // Then
        assertThat(viewName).isEqualTo("ruleName/update");
    }

    @Test
    void testUpdateRuleNameWhenNoError() {
        // Given
        RuleName ruleName = new RuleName();
        RuleName existingRuleName = new RuleName();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(ruleNameServiceMock.findById(1234)).thenReturn(Optional.of(existingRuleName));
        // When
        String viewName = ruleNameController.updateRuleName(1234, ruleName, bindingResultMock);
        // Then
        verify(ruleNameServiceMock).update(existingRuleName, ruleName);
        assertThat(viewName).isEqualTo("redirect:/ruleName/list");
    }
}
