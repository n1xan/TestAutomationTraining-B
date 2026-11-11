package pages.newRequestPage;

import data.AdditionalDetailsFromData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestAdditionalDetailsPage extends FormPage<AdditionalDetailsFromData> {

    protected NewRequestAdditionalDetailsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public void fillForm(AdditionalDetailsFromData formData) {

    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
