package tests;

import base.BaseTest;
import components.Header;
import components.MyRequestGrid;
import data.LoginFromData;
import data.RequestRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.LoginPage;
import pages.MyRequestsPage;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static data.RequestRow.IS_OPEN;
import static data.RequestRow.IS_URGENT;

public class LambdaTests extends BaseTest {
    private static final String EMAIL = "john@example.com";
    private static final String PASSWORD = "password123";
    private static final By MY_REQUESTS_LINK = By.cssSelector("[data-testid='cs-nav-my-requests']");

    private Header header;
    private MyRequestsPage myRequestsPage;
    private MyRequestGrid grid;

    @BeforeEach
    void login() {
        LoginPage loginPage = new LoginPage(driver, wait, BASE_URL).open();
        header = new Header(driver, wait);
        myRequestsPage = new MyRequestsPage(driver, wait);
        grid = myRequestsPage.grid();

        loginPage.fillForm(new LoginFromData(EMAIL, PASSWORD));
        loginPage.submitForm();

        // Was: wait.until(ExpectedConditions.urlContains("/dashboard"));
        wait.until(d -> d.getCurrentUrl().contains("/dashboard"));
    }

    // Exercise 1: replace an ExpectedConditions wait with a lambda.
    // Returning null or false keeps the wait polling.
    @Test
    void waitWithLambda() {
        // Was: wait.until(ExpectedConditions.elementToBeClickable(MY_REQUESTS_LINK)).click();
        WebElement link = wait.until(d -> {
            WebElement element = d.findElement(MY_REQUESTS_LINK);
            return element.isDisplayed() && element.isEnabled() ? element : null;
        });
        link.click();

        // Was: wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(GRID_ROWS, 0));
        Boolean gridHasRows = wait.until(d -> d.findElements(By.cssSelector(".MuiDataGrid-row")).size() > 0);

        // Grid.getRowElements() uses the same idea: empty list -> null -> keep waiting.
        Assertions.assertAll(
                () -> Assertions.assertTrue(gridHasRows),
                () -> Assertions.assertFalse(grid.getRowElements().isEmpty(), "The grid has no rows"));
    }

    // Exercise 2: Predicate<RequestRow> isUrgent / isOpen combined with and() and negate().
    @Test
    void filterRowsWithPredicates() {
        header.openMyRequests();
        List<RequestRow> rows = grid.getRows();

        Predicate<RequestRow> urgentAndOpen = IS_URGENT.and(IS_OPEN);
        Predicate<RequestRow> notUrgent = IS_URGENT.negate();
        Predicate<RequestRow> finished = IS_OPEN.negate();

        List<RequestRow> urgentOpenRows = grid.getRows(urgentAndOpen);
        List<RequestRow> notUrgentRows = rows.stream().filter(notUrgent).toList();
        List<RequestRow> finishedRows = rows.stream().filter(finished).toList();

        System.out.println("Urgent & open: " + urgentOpenRows);
        System.out.println("Not urgent:    " + notUrgentRows);
        System.out.println("Finished:      " + finishedRows);

        Assertions.assertAll(
                () -> Assertions.assertEquals(urgentOpenRows, grid.getUrgentOpenRequests()),
                () -> Assertions.assertTrue(urgentOpenRows.stream().allMatch(IS_URGENT), "Non-urgent row in urgent list"),
                () -> Assertions.assertTrue(urgentOpenRows.stream().allMatch(IS_OPEN), "Finished row in open list"),
                () -> Assertions.assertTrue(notUrgentRows.stream().noneMatch(IS_URGENT), "Urgent row in non-urgent list"),
                () -> Assertions.assertEquals(rows.size(),
                        rows.stream().filter(IS_OPEN).count() + finishedRows.size(),
                        "isOpen and isOpen.negate() should split the rows in two"));
    }

    // Exercise 3: map grid rows with Function<WebElement, RequestRow>, print with Consumer<RequestRow>, use andThen().
    @Test
    void mapAndPrintRowsWithFunctionAndConsumer() {
        header.openMyRequests();
        List<WebElement> rowElements = grid.getRowElements();

        Function<WebElement, RequestRow> toRow = grid.rowMapper();
        // Function.andThen: WebElement -> RequestRow -> String
        Function<WebElement, String> toSummary = toRow.andThen(
                r -> "%s [%s / %s]".formatted(r.title(), r.priority(), r.status()));

        Consumer<RequestRow> print = r -> System.out.println("Row: " + r);
        Consumer<RequestRow> warnIfUrgent = r -> {
            if (IS_URGENT.and(IS_OPEN).test(r)) {
                System.out.println("   ^ URGENT and still open!");
            }
        };
        // Consumer.andThen: run both consumers for each row, in order
        Consumer<RequestRow> printAndWarn = print.andThen(warnIfUrgent);

        List<RequestRow> rows = rowElements.stream().map(toRow).toList();
        rows.forEach(printAndWarn);
        rowElements.stream().map(toSummary).forEach(System.out::println);

        Assertions.assertAll(
                () -> Assertions.assertEquals(rowElements.size(), rows.size()),
                () -> Assertions.assertEquals(myRequestsPage.getLatestRequestTitle(), rows.get(0).title()),
                () -> Assertions.assertTrue(rows.stream().noneMatch(r -> r.title().isBlank()), "A row has an empty title"));
    }

    // Generic grid: the record field names must match the grid column headers.
    @Test
    void gridHeadersMatchRecordFields() {
        header.openMyRequests();

        System.out.println("Grid headers:  " + grid.getHeaders());
        System.out.println("Record fields: " + grid.getFieldNames());

        Assertions.assertDoesNotThrow(grid::verifyHeaders);
        Assertions.assertTrue(grid.getHeaders().containsAll(grid.getFieldNames()));
    }
}
