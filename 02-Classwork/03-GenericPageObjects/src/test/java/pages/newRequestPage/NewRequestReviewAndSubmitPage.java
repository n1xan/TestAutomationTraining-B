package pages.newRequestPage;

import data.ReviewAndSubmitFromData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestReviewAndSubmitPage extends FormPage<ReviewAndSubmitFromData> {
    protected NewRequestReviewAndSubmitPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public void fillForm(ReviewAndSubmitFromData formData) {

    }

    @Override
    public void submitForm() {

    }
}
