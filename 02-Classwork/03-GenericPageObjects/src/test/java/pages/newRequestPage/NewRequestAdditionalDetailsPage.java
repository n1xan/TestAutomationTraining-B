package pages.newRequestPage;

import data.AdditionalDetailsFromData;
import pages.FormPage;

public class NewRequestAdditionalDetailsPage extends FormPage<AdditionalDetailsFromData> {
    @Override
    public void fillForm(AdditionalDetailsFromData formData) {

    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
