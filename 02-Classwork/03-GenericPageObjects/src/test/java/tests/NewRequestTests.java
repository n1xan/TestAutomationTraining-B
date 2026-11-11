package tests;

import base.BaseTest;
import components.Header;
import data.BasicInfoFromData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.MyRequestsPage;
import pages.newRequestPage.NewRequestBasicInfoPage;
import pages.newRequestPage.NewRequestPage;

public class NewRequestTests extends BaseTest {

    private LoginPage loginPage;
    private Header header;
    private NewRequestPage newRequestPage;
    private NewRequestBasicInfoPage newRequestBasicInfoPage;
    private MyRequestsPage myRequestsPage;

    private static final String EMAIL = "john@example.com";
    private static final String PASSWORD = "password123";

    @BeforeEach
    void setupTest() {

        loginPage = new LoginPage(driver, wait, BASE_URL).open();
        header = new Header(driver, wait);
        newRequestBasicInfoPage = new NewRequestBasicInfoPage(driver, wait);
//        newRequestPage = new NewRequestPage(driver, wait);
//        newRequestPage = new NewRequestPage(driver, wait);
//        newRequestPage = new NewRequestPage(driver, wait);
        newRequestPage = new NewRequestPage(driver, wait);
        myRequestsPage = new MyRequestsPage(driver);

        loginPage.login(EMAIL, PASSWORD);
    }

    @Test
    void createHighPriorityRequest() {

        String title = "Unusual traffic";

        header.openNewRequest();
        var basicInfo = new BasicInfoFromData(
                "Issue Title",
                "This is a high priority request for something happened on the road",
                "10/11/2026",
                "Roads & Infrastructure",
                "High",
                true);

        newRequestBasicInfoPage.fillForm(basicInfo);
        newRequestBasicInfoPage.submitForm();
        newRequestPage
                ///Next method is for "Critical Requests Set to Low Priority" scenario
                .inputStreetAddress("Main Street 1")
                .inputCity("Sofia")
                .inputPostalCode("1000")
                .inputLocationDescription("Near city center")
                .clickOnNext()
                .selectContactMethod("EMAIL")
                .inputEmail("john@example.com")
                .inputPhone("088123")
                ///Next method is for "Critical Requests Set to Low Priority" scenario
//              .inputAlternativePhone("123123")
                .clickOnNext()
                .acceptTerms()
                .clickOnNext()
                .submitRequest();

        ///  Assertions for the scenario with setup „Service Request Creation Fails“
//        Assertions.assertFalse(newRequestPage.isRequestErrorMessageDisplayed(),"Unable to create service request at this time");

        header.openMyRequests();

        ///  Assertions and for the scenario with setup „Critical Requests Set to Low Priority“
        Assertions.assertAll(() -> Assertions.assertEquals(title, myRequestsPage.getLatestRequestTitle(), "Latest request title is incorrect."),
                () -> Assertions.assertEquals("Submitted", myRequestsPage.getLatestRequestStatus(), "Status is incorrect."),
                () -> Assertions.assertEquals("High", myRequestsPage.getLatestRequestPriority(), "Priority is incorrect."));
    }
}