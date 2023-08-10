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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowUpdateFormTest {

    @InjectMocks
    private CurveController curveController;

    @Mock
    private CurvePointService curvePointServiceMock;

    @Test
    public void testShowUpdateFormWhenCurvePointExist() {
        // Given
        Model model = new ExtendedModelMap();
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointServiceMock.findById(1234)).thenReturn(Optional.of(curvePoint));
        // When
        String viewName = curveController.showUpdateForm(1234, model);
        // Then
        assertThat(model.getAttribute("curvePoint")).isEqualTo(curvePoint);
        assertThat(viewName).isEqualTo("curvePoint/update");
    }

    @Test
    public void testShowUpdateFormWhenCurvePointDontExist() {
        // Given
        Model model = new ExtendedModelMap();
        when(curvePointServiceMock.findById(1234)).thenReturn(Optional.empty());
        // When
        String viewName = curveController.showUpdateForm(1234, model);
        // Then
        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
    }
}
