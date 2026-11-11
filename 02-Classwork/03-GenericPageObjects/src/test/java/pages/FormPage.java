package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class FormPage<TForm> {
    protected WebDriverWait wait;
    protected WebDriver driver;


    protected static final By NEXT_BUTTON = By.xpath("//*[@data-testid='cs-new-request-wizard-next']");
    protected static final By CANCEL_BUTTON = By.xpath("//*[@data-testid='cs-new-request-wizard-cancel']");

    protected void click(By locator) {wait.until(ExpectedConditions.elementToBeClickable(locator)).click();}

    public abstract void fillForm(TForm formData);
    public abstract void submitForm();
}
