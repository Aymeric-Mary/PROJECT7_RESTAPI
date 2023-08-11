package com.nnk.springboot.UT.controllers.curvePoint;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteCurvePointTest {

    @InjectMocks
    private CurveController curveController;

    @Mock
    private CurvePointService curvePointServiceMock;

    @Test
    public void testCurvePointDelete() {
        // Given
        // When
        String viewName = curveController.deleteBid(1);
        // Then
        verify(curvePointServiceMock).deleteById(1);
        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
    }
}
