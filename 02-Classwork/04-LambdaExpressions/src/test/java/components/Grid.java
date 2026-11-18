package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

// rowFactory receives the cell texts in the same order as columns (the grid's data-field values).
public class Grid<TRow> {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final List<String> columns;
    private final Function<List<String>, TRow> rowMapping;

    public Grid(WebDriver driver, WebDriverWait wait, List<String> columns, Function<List<String>, TRow> rowMapping) {
        this.driver = driver;
        this.wait = wait;
        this.columns = columns;
        this.rowMapping = rowMapping;
    }

    // Locators for Grid components - can be overridden if some other locators are needed
    protected By root() {
        return By.cssSelector(".MuiDataGrid-root");
    }

    protected By headers() {
        return By.cssSelector(".MuiDataGrid-columnHeader[data-field]");
    }

    protected By rows() {
        return By.cssSelector(".MuiDataGrid-row");
    }

    protected By cell(String column) {
        return By.cssSelector("[data-field='" + column + "']");
    }

    public List<String> getHeaders() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(root())).findElements(headers()).stream().map(h -> h.getDomAttribute("data-field")).toList();
    }

    public Grid<TRow> verifyHeaders() {
        List<String> headers = getHeaders();
        if (!headers.containsAll(columns)) {
            throw new IllegalStateException("Expected columns %s, grid columns %s".formatted(columns, headers));
        }
        return this;
    }

    public List<WebElement> getRowElements() {
        return wait.until(d -> {
            List<WebElement> rows = d.findElement(root()).findElements(rows());
            return rows.isEmpty() ? null : rows;
        });
    }

    public Function<WebElement, TRow> rowMapper() {
        return this::toRow;
    }

    public List<TRow> getRows() {
        return getRowElements().stream().map(this::toRow).toList();
    }

    public List<TRow> getRows(Predicate<TRow> filter) {
        return getRows().stream().filter(filter).toList();
    }

    public TRow getRow(int index) {
        return toRow(getRowElements().get(index));
    }

    private TRow toRow(WebElement row) {
        List<String> cells = columns.stream()
                .map(c -> row.findElement(cell(c)).getText().trim())
                .toList();
        return rowMapping.apply(cells);
    }
}
