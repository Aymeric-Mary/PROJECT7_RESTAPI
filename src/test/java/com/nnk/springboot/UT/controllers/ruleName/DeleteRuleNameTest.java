package com.nnk.springboot.UT.controllers.ruleName;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteRuleNameTest {

    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameServiceMock;

    @Test
    void testRuleNameDelete() {
        // Given
        // When
        String viewName = ruleNameController.deleteRuleName(1);
        // Then
        verify(ruleNameServiceMock).deleteById(1);
        assertThat(viewName).isEqualTo("redirect:/ruleName/list");
    }
}
