package tests;

import base.BaseTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.Header;
import data.LoginFromData;
import data.Priority;
import data.RequestRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DataDrivenLoginTests extends BaseTest {

    private static final String CITIZEN_EMAIL = "john@example.com";
    private static final String PASSWORD = "password123";

    private LoginPage loginPage;
    private Header header;

    @BeforeEach
    void openLoginPage() {
        loginPage = new LoginPage(driver, wait, BASE_URL).open();
        header = new Header(driver, wait);
    }

    @ParameterizedTest(name = "{2} user Logs in successfully with valid credentials.")
    @CsvFileSource(resources = "/loginCredentials.csv")
    void userLogsInSuccessfully_verifyRole_andLandingPage(String username, String password, String role, String landingPage) {
        var loginData = new LoginFromData(username, password);
        loginPage.fillForm(loginData);
        loginPage.submitForm();

        wait.until(ExpectedConditions.urlContains(landingPage));
        Assertions.assertTrue(header.isDisplayed(), "Header is not displayed after login");
        Assertions.assertEquals(role, header.getUserRole(), "Wrong role in the user menu");

        header.logout();
        Assertions.assertTrue(loginPage.isLoginButtonDisplayed(), "Login page is not shown after logout");
    }
}
