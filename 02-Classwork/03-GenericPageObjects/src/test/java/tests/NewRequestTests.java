package tests;

import base.BaseTest;
import components.Header;
import data.AdditionalDetailsFromData;
import data.BasicInfoFromData;
import data.ContactInformationFromData;
import data.LocationDetailsFromData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.MyRequestsPage;
import pages.newRequestPage.*;

public class NewRequestTests extends BaseTest {

    private LoginPage loginPage;
    private Header header;
    private NewRequestBasicInfoPage newRequestBasicInfoPage;
    private NewRequestAdditionalDetailsPage newRequestAdditionalDetailsPage;
    private NewRequestContactInformationPage newRequestContactInformationPage;
    private NewRequestLocationDetailsPage newRequestLocationDetailsPage;
    private NewRequestReviewAndSubmitPage newRequestReviewAndSubmitPage;
    private MyRequestsPage myRequestsPage;

    private static final String EMAIL = "john@example.com";
    private static final String PASSWORD = "password123";

    @BeforeEach
    void setupTest() {

        loginPage = new LoginPage(driver, wait, BASE_URL).open();
        header = new Header(driver, wait);
        newRequestBasicInfoPage = new NewRequestBasicInfoPage(driver, wait);
        newRequestAdditionalDetailsPage = new NewRequestAdditionalDetailsPage(driver, wait);
        newRequestLocationDetailsPage = new NewRequestLocationDetailsPage(driver, wait);
        newRequestContactInformationPage = new NewRequestContactInformationPage(driver, wait);
        newRequestReviewAndSubmitPage = new NewRequestReviewAndSubmitPage(driver, wait);

        myRequestsPage = new MyRequestsPage(driver);

        loginPage.login(EMAIL, PASSWORD);
    }

    @Test
    void createHighPriorityRequest() {

        header.openNewRequest();

        var basicInfo = new BasicInfoFromData(
                "Issue Title",
                "This is a high priority request for something happened on the road",
                "11/10/2026",
                "Roads & Infrastructure",
                "High",
                true);

        var locationData = new LocationDetailsFromData(
                "Main Street 1",
                "Sofia",
                "1000",
                "Near city center"
        );

        ContactInformationFromData contactInfoData = new ContactInformationFromData(
                "EMAIL",
                "john@example.com",
                "088123",
                "123123"
        );

        AdditionalDetailsFromData additionalDetailsFromData = new AdditionalDetailsFromData(
                true
        );

        newRequestBasicInfoPage.fillForm(basicInfo);
        newRequestBasicInfoPage.submitForm();

        newRequestLocationDetailsPage.fillForm(locationData);
        newRequestLocationDetailsPage.submitForm();

        newRequestContactInformationPage.fillForm(contactInfoData);
        newRequestContactInformationPage.submitForm();

        newRequestAdditionalDetailsPage.fillForm(additionalDetailsFromData);
        newRequestAdditionalDetailsPage.submitForm();

        newRequestReviewAndSubmitPage.submitRequest();

        ///  Assertions for the scenario with setup „Service Request Creation Fails“
//        Assertions.assertFalse(newRequestPage.isRequestErrorMessageDisplayed(),"Unable to create service request at this time");

        header.openMyRequests();

        ///  Assertions and for the scenario with setup „Critical Requests Set to Low Priority“
        Assertions.assertAll(() -> Assertions.assertEquals(basicInfo.title(), myRequestsPage.getLatestRequestTitle(), "Latest request title is incorrect."),
                () -> Assertions.assertEquals("Submitted", myRequestsPage.getLatestRequestStatus(), "Status is incorrect."),
                () -> Assertions.assertEquals(basicInfo.priority(), myRequestsPage.getLatestRequestPriority(), "Priority is incorrect."));
    }
}