package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to delete an email from the computer.
 */
public class EmailDeleteEvent extends BaseEvent {

    public final String data;

    public EmailDeleteEvent(String fileName) {
        data = fileName;
    }

    @Override
    public String toString() {
        return data;
    }

}
