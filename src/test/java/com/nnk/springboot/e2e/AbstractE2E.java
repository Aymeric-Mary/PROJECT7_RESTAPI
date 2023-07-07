package com.nnk.springboot.e2e;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractE2E {
    @LocalServerPort
    protected int port;
    protected WebDriver driver;
    protected String baseUrl;

    @MockBean
    private UserRepository userRepository;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void login(String email, String password) {
        driver.get(baseUrl + "/login");
        WebElement inputEmail = driver.findElement(By.id("username"));
        WebElement inputPassword = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type=submit]"));

        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    protected User mockUserRepository(String username, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        return user;
    }

}
