package components;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Generic MUI DataGrid component.
 * Every field of the row record must match a column header's {@code data-field} attribute,
 * e.g. {@code record RequestRow(String title, String priority, String status)} reads the
 * "title", "priority" and "status" columns.
 *
 * @param <TRow> the record type one grid row is mapped to
 */
public class Grid<TRow extends Record> {
    private static final By GRID_ROOT = By.cssSelector(".MuiDataGrid-root");
    private static final By HEADERS = By.cssSelector(".MuiDataGrid-columnHeader[data-field]");
    private static final By ROWS = By.cssSelector(".MuiDataGrid-row");

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final Class<TRow> rowType;
    private final By root;
    private final RecordComponent[] fields;
    private final Constructor<TRow> constructor;

    public Grid(WebDriver driver, WebDriverWait wait, Class<TRow> rowType) {
        this(driver, wait, rowType, GRID_ROOT);
    }

    public Grid(WebDriver driver, WebDriverWait wait, Class<TRow> rowType, By root) {
        this.driver = driver;
        this.wait = wait;
        this.rowType = rowType;
        this.root = root;
        this.fields = rowType.getRecordComponents();
        this.constructor = canonicalConstructor(rowType, fields);
    }

    /** The record field names, which are also the expected column headers. */
    public List<String> getFieldNames() {
        return Arrays.stream(fields).map(RecordComponent::getName).toList();
    }

    /** The data-field values of the grid's column headers. */
    public List<String> getHeaders() {
        return waitForGrid().findElements(HEADERS).stream()
                .map(header -> header.getDomAttribute("data-field"))
                .toList();
    }

    /** Fails if a record field has no matching column header. */
    public Grid<TRow> verifyHeaders() {
        List<String> headers = getHeaders();
        List<String> missing = getFieldNames().stream().filter(Predicate.not(headers::contains)).toList();
        if (!missing.isEmpty()) {
            throw new IllegalStateException("%s fields %s have no grid column. Grid columns: %s"
                    .formatted(rowType.getSimpleName(), missing, headers));
        }
        return this;
    }

    /** Waits until the grid has rows and returns them as raw elements. */
    public List<WebElement> getRowElements() {
        return wait.until(d -> {
            List<WebElement> rows = d.findElement(root).findElements(ROWS);
            return rows.isEmpty() ? null : rows;
        });
    }

    /** Maps one row element to a TRow, reading the cell whose data-field equals each record field name. */
    public Function<WebElement, TRow> rowMapper() {
        return this::toRow;
    }

    public List<TRow> getRows() {
        return getRowElements().stream().map(rowMapper()).toList();
    }

    public List<TRow> getRows(Predicate<TRow> filter) {
        return getRows().stream().filter(filter).toList();
    }

    public Optional<TRow> findFirst(Predicate<TRow> filter) {
        return getRows().stream().filter(filter).findFirst();
    }

    public TRow getRow(int index) {
        return toRow(getRowElements().get(index));
    }

    private TRow toRow(WebElement row) {
        Object[] values = Arrays.stream(fields)
                .map(field -> convert(cellText(row, field.getName()), field.getType()))
                .toArray();
        try {
            return constructor.newInstance(values);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot create " + rowType.getSimpleName(), e);
        }
    }

    private WebElement waitForGrid() {
        return wait.until(d -> {
            WebElement grid = d.findElement(root);
            return grid.isDisplayed() ? grid : null;
        });
    }

    private static String cellText(SearchContext row, String field) {
        return row.findElement(By.cssSelector("[data-field='" + field + "']")).getText().trim();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Object convert(String text, Class<?> type) {
        if (type == String.class) return text;
        if (type == int.class || type == Integer.class) return Integer.parseInt(text);
        if (type == long.class || type == Long.class) return Long.parseLong(text);
        if (type == double.class || type == Double.class) return Double.parseDouble(text);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(text);
        if (type.isEnum()) return Enum.valueOf((Class<Enum>) type, text.toUpperCase().replace(' ', '_'));
        throw new IllegalArgumentException("Unsupported grid field type: " + type.getName());
    }

    private static <T extends Record> Constructor<T> canonicalConstructor(Class<T> type, RecordComponent[] fields) {
        Class<?>[] types = Arrays.stream(fields).map(RecordComponent::getType).toArray(Class<?>[]::new);
        try {
            return type.getDeclaredConstructor(types);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("No canonical constructor for " + type.getName(), e);
        }
    }
}
