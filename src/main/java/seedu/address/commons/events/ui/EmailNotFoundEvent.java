package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author EatOrBeEaten

/**
 * Indicates that specified Email cannot be found in local directory.
 */
public class EmailNotFoundEvent extends BaseEvent {

    public final String message = "Error: Email with subject \"%s\" cannot be found";

    public final String data;

    public EmailNotFoundEvent(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format(message, data);
    }
}
