package pages.newRequestPage;

import data.ReviewAndSubmitFromData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestReviewAndSubmitPage extends FormPage<ReviewAndSubmitFromData> {
    public NewRequestReviewAndSubmitPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private static final By SUBMIT_REQUEST = By.xpath("//*[@data-testid='cs-new-request-wizard-submit']");
    private static final By REQUEST_ERROR_MESSAGE = By.xpath("//*[@data-testid='cs-submit-error']");

    @Override
    public void fillForm(ReviewAndSubmitFromData formData) {

    }

    @Override
    public void submitForm() {
        submitRequest();
    }


    public boolean isRequestErrorMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(REQUEST_ERROR_MESSAGE)).isDisplayed();
    }

    public NewRequestReviewAndSubmitPage submitRequest() {
        click(SUBMIT_REQUEST);
        return this;
    }
}