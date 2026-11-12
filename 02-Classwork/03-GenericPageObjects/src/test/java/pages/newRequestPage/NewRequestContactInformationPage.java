package pages.newRequestPage;

import data.ContactInformationFromData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestContactInformationPage extends FormPage<ContactInformationFromData> {

    public NewRequestContactInformationPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    private static final By CONTACT_METHOD = By.xpath("//*[@id='mui-component-select-contactMethod']");
    private static final By EMAIL_METHOD = By.xpath("//li[@data-value='EMAIL']");
    private static final By EMAIL_FIELD = By.xpath("//*[@name='email']");
    private static final By PHONE_FIELD = By.xpath("//*[@name='phone']");
    private static final By ALTERNATIVE_PHONE = By.xpath("//input[@name='alternatePhone']");

    public NewRequestContactInformationPage selectContactMethod(String method) {
        driver.findElement(CONTACT_METHOD).click();
        driver.findElement(EMAIL_METHOD).click();
        return this;
    }

    public NewRequestContactInformationPage inputEmail(String email) {
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        return this;
    }

    public NewRequestContactInformationPage inputPhone(String phone) {
        driver.findElement(PHONE_FIELD).sendKeys(phone);
        return this;
    }

    private NewRequestContactInformationPage inputAlternativePhone(String phone) {
        driver.findElement(ALTERNATIVE_PHONE).sendKeys(phone);
        return this;
    }

    @Override
    public void fillForm(ContactInformationFromData formData) {
        selectContactMethod(formData.contactMethod());
        inputEmail(formData.email());
        inputPhone(formData.phone());
        inputAlternativePhone(formData.alternativePhone());
    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
