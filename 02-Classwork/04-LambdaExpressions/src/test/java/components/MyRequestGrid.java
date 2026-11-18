package components;

import data.RequestRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static data.RequestRow.IS_OPEN;
import static data.RequestRow.IS_URGENT;

/** The "My Requests" grid, typed to RequestRow. */
public class MyRequestGrid extends Grid<RequestRow> {

    public MyRequestGrid(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, RequestRow.class);
    }

    public List<RequestRow> getUrgentOpenRequests() {
        return getRows(IS_URGENT.and(IS_OPEN));
    }

    public List<RequestRow> getRequestsWithStatus(String status) {
        return getRows(row -> row.status().equalsIgnoreCase(status));
    }
}
