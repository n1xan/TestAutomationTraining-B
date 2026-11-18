package pages;

import base.BasePage;
import components.MyRequestGrid;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyRequestsPage extends BasePage {

    private final MyRequestGrid grid;

    public MyRequestsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.grid = new MyRequestGrid(driver, wait);
    }

    public MyRequestGrid grid() {
        return grid;
    }

    public String getLatestRequestTitle() {
        return grid.getRow(0).title();
    }

    public String getLatestRequestPriority() {
        return grid.getRow(0).priority();
    }

    public String getLatestRequestStatus() {
        return grid.getRow(0).status();
    }
}
