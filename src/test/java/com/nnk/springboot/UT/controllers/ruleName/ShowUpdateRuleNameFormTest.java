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
import org.springframework.ui.Model;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowUpdateRuleNameFormTest {

    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameServiceMock;

    @Test
    void testShowUpdateFormWhenRuleNameExist() {
        // Given
        Model model = new ExtendedModelMap();
        RuleName ruleName = new RuleName();
        when(ruleNameServiceMock.findById(1234)).thenReturn(Optional.of(ruleName));
        // When
        String viewName = ruleNameController.showUpdateForm(1234, model);
        // Then
        assertThat(model.getAttribute("ruleName")).isEqualTo(ruleName);
        assertThat(viewName).isEqualTo("ruleName/update");
    }

    @Test
    void testShowUpdateFormWhenRuleNameDontExist() {
        // Given
        Model model = new ExtendedModelMap();
        when(ruleNameServiceMock.findById(1234)).thenReturn(Optional.empty());
        // When
        String viewName = ruleNameController.showUpdateForm(1234, model);
        // Then
        assertThat(viewName).isEqualTo("redirect:/ruleName/list");
    }
}
