package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TrivialWebDriverTest {

    @LocalServerPort
    private int port;

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Matti", "Marc", "Marco"})
    public void testUsingSeleniumWebDriver(final String name) {
        driver.get("http://localhost:" + port + "/");
        // use rather long wait if running in dev mode (front-end build, no js cache files committed)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
        
        WebElement msgParagraph = driver.findElement(By.id("message"));
        
        assertThat(msgParagraph.getText()).isEmpty();
        
        driver.findElement(By.xpath("//vaadin-text-field/input")).sendKeys(name);
        
        driver.findElement(By.tagName("vaadin-button")).click();
        
        // Unlike with TestBench, we'll need to wait for the request from server
        // to return in some places. In this example we have simulated slow backend
        // with 1000ms sleep in the click listeners, wait at least 5 second 
        // to get the desired result...
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElement(msgParagraph, name));
        
        assertThat(msgParagraph.getText()).contains(name);
        
    }

}
