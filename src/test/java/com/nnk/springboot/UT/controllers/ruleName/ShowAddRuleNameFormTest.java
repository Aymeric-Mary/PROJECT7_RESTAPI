package com.nnk.springboot.UT.controllers.ruleName;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ShowAddRuleNameFormTest {

    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameServiceMock;

    @Test
    void testShowAddForm() {
        // Given
        RuleName ruleName = new RuleName();
        // When
        String viewName = ruleNameController.addRuleNameForm(ruleName);
        // Then
        assertThat(viewName).isEqualTo("ruleName/add");
    }
}
