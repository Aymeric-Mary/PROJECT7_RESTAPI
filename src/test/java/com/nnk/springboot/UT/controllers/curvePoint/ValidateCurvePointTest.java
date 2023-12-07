package com.nnk.springboot.UT.controllers.curvePoint;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
class ValidateCurvePointTest {

    @InjectMocks
    private CurveController curveController;

    @Mock
    private CurvePointService curvePointServiceMock;

    @Mock
    private BindingResult bindingResultMock;

    @Test
    void testValidateWhenDontHasErrors() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        // When
        String viewName = curveController.validate(curvePoint, bindingResultMock, new ExtendedModelMap());
        // Then
        verify(curvePointServiceMock, times(1)).create(curvePoint);
        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
    }

    @Test
    void testValidateWhenHasErrors() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = curveController.validate(curvePoint, bindingResultMock, new ExtendedModelMap());
        // Then
        verifyNoInteractions(curvePointServiceMock);
        assertThat(viewName).isEqualTo("curvePoint/add");
    }
}
