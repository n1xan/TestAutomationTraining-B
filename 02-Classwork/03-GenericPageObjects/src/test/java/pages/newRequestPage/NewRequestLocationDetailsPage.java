package pages.newRequestPage;

import data.BasicInfoFromData;
import data.LocationDetailsFromData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestLocationDetailsPage extends FormPage<LocationDetailsFromData> {
    private static final By STREET_ADDRESS = By.xpath("//*[@name='streetAddress']");
    private static final By CITY = By.xpath("//*[@name='city']");
    private static final By POSTAL_CODE = By.xpath("//*[@name='postalCode']");
    private static final By LOCATION = By.xpath("//*[@name='locationText']");
    private static final By NEXT_BUTTON = By.xpath("//*[@data-testid='cs-new-request-wizard-next']");

    public NewRequestLocationDetailsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    public NewRequestLocationDetailsPage inputStreetAddress(String address) {
        driver.findElement(STREET_ADDRESS).sendKeys(address);
        return this;
    }

    public NewRequestLocationDetailsPage inputCity(String city) {
        driver.findElement(CITY).sendKeys(city);
        return this;
    }

    public NewRequestLocationDetailsPage inputPostalCode(String postalCode) {
        driver.findElement(POSTAL_CODE).sendKeys(postalCode);
        return this;
    }

    public NewRequestLocationDetailsPage inputLocationDescription(String location) {
        driver.findElement(LOCATION).sendKeys(location);
        return this;
    }
    @Override
    public void fillForm(LocationDetailsFromData formData) {

        inputStreetAddress(formData.address());
        inputCity(formData.city());
        inputPostalCode(formData.postalCode());
        inputLocationDescription(formData.locationDescription());
    }

    @Override
    public void submitForm () {
        click(NEXT_BUTTON);
    }
}