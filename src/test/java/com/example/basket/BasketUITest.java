
package com.example.basket;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BasketUITest {

    private static WebDriver driver;

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1200,800");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterAll
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Selecting Apple, Apple, Banana shows £0.90")
    void calculatesMixedBasket() {
        Path html = Paths.get("src", "test", "resources", "basket.html").toAbsolutePath();
        driver.get(html.toUri().toString());

        List<WebElement> checkboxes = driver.findElements(By.cssSelector("#items input[type='checkbox']"));// add the correct selctor 
        // Select first Apple, second Apple, and first Banana
        checkboxes.get(0).click(); // Apple
        checkboxes.get(1).click(); // Apple
        checkboxes.get(2).click(); // Banana

        driver.findElement(By.id("calc")).click();
        String totalText = driver.findElement(By.id("total")).getText();
        assertTrue(totalText.contains("£0.90"), "Expected total to contain £0.90 but was: " + totalText);
    }
}
