package pages.newRequestPage;

import data.AdditionalDetailsFromData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestAdditionalDetailsPage extends FormPage<AdditionalDetailsFromData> {
    private static final By TERMS_BUTTON = By.xpath("//*[@data-testid='cs-new-request-agree-terms']");

    public NewRequestAdditionalDetailsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public void fillForm(AdditionalDetailsFromData formData) {

        if (formData.shouldAcceptTerms()) {
            acceptTerms();
        }
    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }

    public NewRequestAdditionalDetailsPage acceptTerms() {
        click(TERMS_BUTTON);
        return this;
    }
}