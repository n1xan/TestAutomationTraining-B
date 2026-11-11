import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTests {
    private static final String BASE_URL = System.getProperty("baseUrl", "http://localhost:5173");
    // Shared password of all demo accounts (see the demo app README).
    private static final String DEMO_PASSWORD = "password123";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.addArguments("--window-size=1400,900");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void loginPageHasCorrectTitleAndButton() {
        driver.get(BASE_URL + "/login");
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='cs-login-submit']")));

        Assertions.assertEquals("City Services Portal", driver.getTitle(), "Page title is wrong");
        Assertions.assertEquals("Login", loginButton.getText(), "Login button text is wrong");
    }

    @Test
    void citizenCanLogin() {
        loginAndCheckRole("john@example.com", "CITIZEN", "/dashboard");
    }

    @Test
    void clerkCanLogin() {
        loginAndCheckRole("mary.clerk@city.gov", "CLERK", "/clerk/inbox");
    }

    @Test
    void supervisorCanLogin() {
        loginAndCheckRole("supervisor@city.gov", "SUPERVISOR", "/supervisor/dashboard");
    }

    @Test
    void fieldAgentCanLogin() {
        loginAndCheckRole("field.agent@city.gov", "FIELD_AGENT", "/agent/my-tasks");
    }

    @Test
    void adminCanLogin() {
        loginAndCheckRole("admin@city.gov", "ADMIN", "/admin/database");
    }

    // Logs in, waits for the role's start page and checks the role chip in the user menu.
    private void loginAndCheckRole(String email, String expectedRole, String expectedPath) {
        driver.get(BASE_URL + "/login");
        // The data-testid is on the MUI wrapper, the real <input> is inside it.
        driver.findElement(By.cssSelector("[data-testid='cs-login-email'] input")).sendKeys(email);
        driver.findElement(By.cssSelector("[data-testid='cs-login-password'] input")).sendKeys(DEMO_PASSWORD);
        driver.findElement(By.cssSelector("[data-testid='cs-login-submit']")).click();

        wait.withMessage("The " + expectedRole + " start page " + expectedPath + " did not open after login")
                .until(ExpectedConditions.urlContains(expectedPath));

        driver.findElement(By.cssSelector("[data-testid='cs-user-menu-button']")).click();
        WebElement roleChip = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='cs-user-menu'] .MuiChip-label")));
        Assertions.assertEquals(expectedRole, roleChip.getText(), "Wrong role in the user menu");
    }
}
