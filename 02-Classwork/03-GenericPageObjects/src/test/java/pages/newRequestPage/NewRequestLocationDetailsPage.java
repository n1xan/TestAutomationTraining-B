package pages.newRequestPage;

import data.LocationDetailsFromData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestLocationDetailsPage extends FormPage<LocationDetailsFromData> {
    protected NewRequestLocationDetailsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public void fillForm(LocationDetailsFromData formData) {

    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
