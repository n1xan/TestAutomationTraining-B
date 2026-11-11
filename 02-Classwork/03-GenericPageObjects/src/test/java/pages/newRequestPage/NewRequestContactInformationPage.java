package pages.newRequestPage;

import data.ContactInformationFromData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestContactInformationPage extends FormPage<ContactInformationFromData> {
    protected NewRequestContactInformationPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public void fillForm(ContactInformationFromData formData) {

    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
