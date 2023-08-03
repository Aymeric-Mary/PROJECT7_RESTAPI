package com.nnk.springboot.UT.controllers.curvePoint;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListCurvePointTest {

    @InjectMocks
    private CurveController curveController;

    @Mock
    private CurvePointService curvePointServiceMock;

    @Test
    public void testcurvePointList() {
        // Given
        Model model = new ExtendedModelMap();
        List<CurvePoint> curvePoints = List.of(
                new CurvePoint(),
                new CurvePoint()
        );
        when(curvePointServiceMock.findAll()).thenReturn(curvePoints);
        // When
        String viewName = curveController.curvePointList(model);
        // Then
        assertThat(model.getAttribute("curvePoints")).isEqualTo(curvePoints);
        assertThat(viewName).isEqualTo("curvePoint/list");
    }
}
