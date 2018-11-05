package seedu.address.commons.events.model;

import org.simplejavamail.email.Email;

import seedu.address.commons.events.BaseEvent;

//@@author EatOrBeEaten

/**
 * Indicates that an Email has been loaded from local directory.
 */
public class EmailLoadedEvent extends BaseEvent {

    public final Email data;

    public EmailLoadedEvent(Email data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.getSubject();
    }

}
