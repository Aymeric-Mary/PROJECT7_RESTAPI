package com.nnk.springboot.e2e;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginE2ETest extends AbstractE2E {

    @Test
    public void testLogin_whenCredentialsAreGood() {
        // Given
        mockUserRepository("user", "123456");
        // When
        login("user", "123456");
        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/");
    }

    @Test
    public void testLogin_whenCredentialsAreWrong() {
        // Given
        mockUserRepository("user", "123456");
        // When
        login("user", "user");
        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/login?error");
    }
}
