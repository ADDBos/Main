package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author EatOrBeEaten

/**
 * Indicates that an Email is to be loaded from local directory.
 */
public class EmailLoadEvent extends BaseEvent {

    public final String data;

    public EmailLoadEvent(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data;
    }

}
