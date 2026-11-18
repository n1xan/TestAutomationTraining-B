package components;

import data.RequestRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/** The "My Requests" grid, typed to RequestRow. */
public class MyRequestGrid extends Grid<RequestRow> {
    private static final Set<String> URGENT_PRIORITIES = Set.of("high", "critical", "urgent");
    private static final Set<String> FINAL_STATUSES = Set.of("resolved", "closed", "rejected");

    public static final Predicate<RequestRow> IS_URGENT =
            row -> URGENT_PRIORITIES.contains(row.priority().toLowerCase());

    public static final Predicate<RequestRow> IS_OPEN =
            row -> !FINAL_STATUSES.contains(row.status().toLowerCase());

    public MyRequestGrid(WebDriver driver, WebDriverWait wait) {
        super(driver, wait, List.of("title", "priority", "status"),
                cells -> new RequestRow(cells.get(0), cells.get(1), cells.get(2)));
    }

    public List<RequestRow> getUrgentOpenRequests() {
        return getRows(IS_URGENT.and(IS_OPEN));
    }

    public List<RequestRow> getRequestsWithStatus(String status) {
        return getRows(row -> row.status().equalsIgnoreCase(status));
    }
}
