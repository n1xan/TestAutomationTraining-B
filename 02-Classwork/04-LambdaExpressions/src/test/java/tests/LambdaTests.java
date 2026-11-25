package tests;

import base.BaseTest;
import data.RequestRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTests extends BaseTest {

    @Test
    void consumerTest() {
        Consumer<WebDriver> openGoogle = browser -> browser.get("https://google.com");
        openGoogle.accept(driver);
    }

    @Test
    void supplierTest() {
        // Supplier – get(): creates a value on demand, no input
        Supplier<WebDriver> driverSupplier = () -> new ChromeDriver();
        WebDriver driver = driverSupplier.get();
    }

    @Test
    void lambdaTest() {
        List<String> cities = new ArrayList<>();
        cities.add("Berlin");
        cities.add("London");
        cities.add("Paris");
        cities.add("Kiev");

        Runnable printToConsole = () -> System.out.print("Parameterless lambda expression");
        printToConsole.run();
    }

    @Test
    void expectedConditionsTest() {
        driver.get("https://google.com");
        wait.until((browser) -> Objects.equals(browser.getTitle(), "Google"));
//        wait.until((browser) -> browser.findElements(By.xpath("spinnerLocator|spinnerLocator|spinnerLocator")).isEmpty());
    }

    // Write Predicate<RequestRow> isUrgent and isOpen
    // (not Resolved, Closed, Rejected). Combine them with and() and negate()
    @Test
    void predicateTest() {
        List<RequestRow> rows = List.of(
                new RequestRow("Laptop not booting", "High", "Open"),
                new RequestRow("Need new mouse", "Low", "Open"),
                new RequestRow("Server is down", "Critical", "In Progress"),
                new RequestRow("VPN access", "Medium", "Resolved"),
                new RequestRow("Printer jam", "High", "Closed"));

        Predicate<RequestRow> isUrgent = (r) -> r.priority().equals("High") || r.priority().equals("Critical");
        Predicate<RequestRow> isOpen = (r) -> r.status().equals("Open");

        rows.forEach(a -> System.out.println(isUrgent.or(isOpen).test(a) ? "Request is Urgent and Open" : "Request is not Urgent and Open"));

//        rows.forEach(isUrgent::test);

//        for (RequestRow requestRow : rows) {
//            if (isUrgent.test(requestRow)) {
//                System.out.println("Request is Urgent");
//            } else {
//                System.out.println("Request is not Urgent");
//            }
//        }
    }

    @Test
//    @Disabled("Expected to fail - pseudo code")
    void functionTest() {
        List<RequestRow> rows = List.of(
                new RequestRow("ZLaptop not booting", "High", "Open"),
                new RequestRow("Need new mouse", "Low", "Open"),
                new RequestRow("Server is down", "Critical", "In Progress"),
                new RequestRow("VPN access", "Medium", "Resolved"),
                new RequestRow("Printer jam", "High", "Closed"));

        // table tr
//        WebElement gridWrapper = driver.findElement(By.name("gridRowLocator"));

        // table > tr > td[] td[] td
        //              td td td
        //              td td td
//        Function<WebElement, RequestRow> castRowToModel = (element) -> {
//            var tableCells = element.findElements(By.tagName("td"));
//            var title = tableCells.get(0).getText();
//            var priority = tableCells.get(1).getText();
//            var status = tableCells.get(2).getText();
//
//            return new RequestRow(title, priority, status);
//        };

//        RequestRow gridModel = castRowToModel.apply(gridWrapper);
    }

}
