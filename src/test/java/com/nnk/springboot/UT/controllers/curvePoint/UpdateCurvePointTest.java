package com.nnk.springboot.UT.controllers.curvePoint;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
class UpdateCurvePointTest {

    @Mock
    CurvePointService curvePointServiceMock;

    @Mock
    BindingResult bindingResultMock;

    @InjectMocks
    CurveController curvePointController;

    @Test
    void testUpdateCurvePointWhenHasError() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResultMock.hasErrors()).thenReturn(true);
        // When
        String viewName = curvePointController.updateCurvePoint(1234, curvePoint, bindingResultMock);
        // Then
        assertThat(viewName).isEqualTo("curvePoint/update");
    }

    @Test
    void testUpdateCurvePointWhenNoError() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        CurvePoint existingCurvePoint = new CurvePoint();
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(curvePointServiceMock.findById(1234)).thenReturn(Optional.of(existingCurvePoint));
        // When
        String viewName = curvePointController.updateCurvePoint(1234, curvePoint, bindingResultMock);
        // Then
        verify(curvePointServiceMock).update(existingCurvePoint, curvePoint);
        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
    }
}
