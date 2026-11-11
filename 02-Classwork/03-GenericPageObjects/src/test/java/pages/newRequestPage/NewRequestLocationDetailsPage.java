package pages.newRequestPage;

import data.LocationDetailsFromData;
import pages.FormPage;

public class NewRequestLocationDetailsPage extends FormPage<LocationDetailsFromData> {
    @Override
    public void fillForm(LocationDetailsFromData formData) {

    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
