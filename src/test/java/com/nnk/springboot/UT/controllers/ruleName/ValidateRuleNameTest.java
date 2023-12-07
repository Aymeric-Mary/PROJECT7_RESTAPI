package com.nnk.springboot.UT.controllers.ruleName;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateRuleNameTest {

    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameServiceMock;

    @Mock
    private BindingResult bindingResultMock;

    @Test
    void testValidateWhenDontHasErrors() {
        // Given
        RuleName ruleName = new RuleName();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        // When
        String viewName = ruleNameController.validate(ruleName, bindingResultMock, new ExtendedModelMap());
        // Then
        verify(ruleNameServiceMock, times(1)).create(ruleName);
        assertThat(viewName).isEqualTo("redirect:/ruleName/list");
    }

    @Test
    void testValidateWhenHasErrors() {
        // Given
        RuleName ruleName = new RuleName();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = ruleNameController.validate(ruleName, bindingResultMock, new ExtendedModelMap());
        // Then
        verifyNoInteractions(ruleNameServiceMock);
        assertThat(viewName).isEqualTo("ruleName/add");
    }
}
