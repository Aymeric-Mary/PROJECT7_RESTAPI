package com.nnk.springboot.UT.controllers.curvePoint;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCurvePointTest {

    @InjectMocks
    private CurveController curveController;

    @Mock
    private CurvePointService curvePointServiceMock;

    @Test
    void testCurvePointDelete() {
        // Given
        // When
        String viewName = curveController.deleteCurvePoint(1);
        // Then
        verify(curvePointServiceMock).deleteById(1);
        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
    }
}
