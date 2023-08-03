package com.nnk.springboot.IT.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ListCurvePointIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointRepository curvePointRepository;

    @Test
    public void testCurvePointList() throws Exception {
        // Given
        CurvePoint curvePoint1 = CurvePoint.builder()
                .curveId(1)
                .term(10.0)
                .value(15.0)
                .build();
        CurvePoint curvePoint2 = CurvePoint.builder()
                .curveId(2)
                .term(20.0)
                .value(25.0)
                .build();
        when(curvePointRepository.findAll()).thenReturn(List.of(curvePoint1, curvePoint2));
        // When
        mockMvc.perform(get("/curvePoint/list"))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attribute("curvePoints", hasSize(2)))
                .andExpect(model().attribute("curvePoints",List.of(curvePoint1, curvePoint2)));
    }

}
