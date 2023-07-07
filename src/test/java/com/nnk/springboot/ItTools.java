package com.nnk.springboot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import java.time.Instant;

import static org.mockito.Mockito.mockStatic;

public class ItTools {

    private MockedStatic<Instant> mockedInstant;

    @BeforeEach
    protected void setUp() {
        mockedInstant = mockStatic(Instant.class);
    }

    @AfterEach
    protected void tearDown() {
        mockedInstant.close();
    }

    protected Instant mockNow(String date) {
        Instant instant = Instant.parse(date);
        mockedInstant.when(Instant::now).thenReturn(instant);
        return instant;
    }
}
