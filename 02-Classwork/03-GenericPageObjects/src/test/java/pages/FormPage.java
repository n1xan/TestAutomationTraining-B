package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class FormPage<TForm> extends BasePage {
    protected WebDriverWait wait;
    protected WebDriver driver;

    protected FormPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    protected static final By NEXT_BUTTON = By.xpath("//*[@data-testid='cs-new-request-wizard-next']");
    protected static final By CANCEL_BUTTON = By.xpath("//*[@data-testid='cs-new-request-wizard-cancel']");

    public abstract void fillForm(TForm formData);
    public abstract void submitForm();
}
