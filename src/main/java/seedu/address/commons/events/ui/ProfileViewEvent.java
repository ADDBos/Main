package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author javenseow
/**
 * Indicates a request to view a person's profile.
 */
public class ProfileViewEvent extends BaseEvent {
    private final Person personSelected;

    public ProfileViewEvent(Person personSelected) {
        this.personSelected = personSelected;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getPersonSelected() {
        return personSelected;
    }
}
