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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListRuleNameTest {

    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameServiceMock;

    @Test
    void testCurvePointList() {
        // Given
        Model model = new ExtendedModelMap();
        List<RuleName> ruleNames = List.of(
                new RuleName(),
                new RuleName()
        );
        when(ruleNameServiceMock.findAll()).thenReturn(ruleNames);
        // When
        String viewName = ruleNameController.home(model);
        // Then
        assertThat(model.getAttribute("ruleNames")).isEqualTo(ruleNames);
        assertThat(viewName).isEqualTo("ruleName/list");
    }
}
