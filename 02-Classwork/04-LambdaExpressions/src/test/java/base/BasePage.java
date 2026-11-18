package base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import static pages.LoginPage.LOGIN_BUTTON;

public abstract class BasePage {
    protected WebDriverWait wait;
    protected WebDriver driver;
    private String url;

    protected BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public BasePage open() {
        this.driver.get(url);
        waitForPageReady();
        return this;
    }

    public void waitForPageReady() {
        wait.until((browser) -> browser.findElements(By.xpath("spinnerLocator|spinnerLocator|spinnerLocator")).isEmpty());
    }

    public static void userWait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Was: wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    protected void click(By locator) { waitForClickable(locator).click(); }

    /** Lambda wait: returning null (or false) keeps the wait polling. */
    protected WebElement waitForClickable(By locator) {
        return wait.until(d -> {
            WebElement element = d.findElement(locator);
            return element.isDisplayed() && element.isEnabled() ? element : null;
        });
    }

    /** Lambda wait for at least one element; an empty list maps to null so the wait keeps going. */
    protected List<WebElement> waitForElements(By locator) {
        return wait.until(d -> {
            List<WebElement> elements = d.findElements(locator);
            return elements.isEmpty() ? null : elements;
        });
    }

    public void takeScreenshot() {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Path destFile = Path.of("target", "screenshots", "home.png");
        try {
            Files.createDirectories(destFile.getParent());
            Files.copy(srcFile.toPath(), destFile, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getInnerHTML(WebElement element) {
        return element.getDomProperty("innerHTML");
    }

    protected String executeJs() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String)js.executeScript("return document.title");
    }

    protected void addCookie() {
        // Add new cookie
        Cookie cookie = new Cookie("key", "value");

        driver.manage().addCookie(cookie);

        //Get all cookies
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie thisCookie : cookies) {
            System.out.println(thisCookie.getName() + "=" + thisCookie.getValue());
        }

        //Delete Cookie by Name
        driver.manage().deleteCookieNamed("CookieName");
    }

    protected void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void dragAndDrop(WebElement elementFrom, WebElement elementTo) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(elementFrom, elementTo).moveByOffset(10,10).perform();
    }

    protected void switchToFrame(String frameName) {
        driver.switchTo().frame(frameName);
    }

    protected void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    public void switchToFirstTab() {
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        String firstTab = windowHandles.get(0);
        driver.switchTo().window(firstTab);
    }

    public void switchToLastTab() {
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        String lastTab = windowHandles.get(windowHandles.size() - 1);
        driver.switchTo().window(lastTab);
    }
}
