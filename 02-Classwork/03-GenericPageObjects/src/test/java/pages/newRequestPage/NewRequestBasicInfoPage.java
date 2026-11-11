package pages.newRequestPage;

import data.BasicInfoFromData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;

public class NewRequestBasicInfoPage extends FormPage<BasicInfoFromData> {
    private static final By REQUEST_TITLE_FIELD = By.xpath("//*[@name='title']");
    private static final By DESCRIPTION = By.xpath("//*[@name='description']");
    private static final By DATE_OF_REQUEST = By.xpath("//*[@name='dateOfRequest']");
    private static final By CATEGORY_DROPDOWN = By.xpath("//*[@id='mui-component-select-category']");
    private static final By CATEGORY_ROADS = By.xpath("//li[@data-value='roads-and-infrastructure']");
    private static final By HIGH_PRIORITY = By.xpath("//span[normalize-space()='High']");
    private static final By EMERGENCY_BUTTON = By.xpath("//*[@data-testid='cs-new-request-emergency']");

    public NewRequestBasicInfoPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public NewRequestBasicInfoPage inputRequestTitle(String title) {
        driver.findElement(REQUEST_TITLE_FIELD).sendKeys(title);
        return this;
    }

    public NewRequestBasicInfoPage inputDescription(String description) {
        driver.findElement(DESCRIPTION).sendKeys(description);
        return this;
    }

    public NewRequestBasicInfoPage inputDate(String date) {
        driver.findElement(DATE_OF_REQUEST).sendKeys(date);
        return this;
    }

    public NewRequestBasicInfoPage selectCategory() {
        click(CATEGORY_DROPDOWN);
        click(CATEGORY_ROADS);
        return this;
    }

    public NewRequestBasicInfoPage selectPriority() {
        click(HIGH_PRIORITY);
        return this;
    }

    @Override
    public void fillForm(BasicInfoFromData formData) {
        inputRequestTitle(formData.title());
        inputDescription(formData.description());
        inputDate(formData.inputDate());
        selectCategory();
        selectPriority();
        // TODO: Add Emergency
    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
