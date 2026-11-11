package pages.newRequestPage;

import data.ContactInformationFromData;
import pages.FormPage;

public class NewRequestContactInformationPage extends FormPage<ContactInformationFromData> {
    @Override
    public void fillForm(ContactInformationFromData formData) {

    }

    @Override
    public void submitForm() {
        click(NEXT_BUTTON);
    }
}
