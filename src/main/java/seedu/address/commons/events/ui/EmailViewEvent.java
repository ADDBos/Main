package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.EmailModel;

//@@author EatOrBeEaten
/**
 * Indicates a request to view email.
 */
public class EmailViewEvent extends BaseEvent {

    private final EmailModel emailModel;

    public EmailViewEvent(EmailModel emailModel) {
        this.emailModel = emailModel;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public EmailModel getEmailModel() {
        return emailModel;
    }

}
