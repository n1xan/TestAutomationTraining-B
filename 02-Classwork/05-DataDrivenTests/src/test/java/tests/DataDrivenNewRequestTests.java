package tests;

import base.BaseTest;
import components.Header;
import data.AdditionalDetailsFromData;
import data.BasicInfoFromData;
import data.ContactInformationFromData;
import data.LocationDetailsFromData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MyRequestsPage;
import pages.newRequestPage.*;

public class DataDrivenNewRequestTests extends BaseTest {

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

        myRequestsPage = new MyRequestsPage(driver, wait);

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

        newRequestBasicInfoPage.fillForm(basicInfo);
        newRequestBasicInfoPage.submitForm();

        // Assert Page URL
    }
}