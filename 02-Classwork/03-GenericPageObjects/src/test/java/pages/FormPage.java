package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class FormPage<TForm> {
    protected WebDriverWait wait;
    protected WebDriver driver;

    protected FormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    protected static final By NEXT_BUTTON = By.xpath("//*[@data-testid='cs-new-request-wizard-next']");
    protected static final By CANCEL_BUTTON = By.xpath("//*[@data-testid='cs-new-request-wizard-cancel']");

    protected void click(By locator) { wait.until(ExpectedConditions.elementToBeClickable(locator)).click(); }

    public abstract void fillForm(TForm formData);
    public abstract void submitForm();
}
