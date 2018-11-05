package seedu.address.commons.events.ui;

import java.util.Set;

import seedu.address.commons.events.BaseEvent;

//@@author EatOrBeEaten

/**
 * Indicates a request to list emails.
 */
public class ListEmailsEvent extends BaseEvent {

    private String emailListString;

    public ListEmailsEvent(Set<String> emailSet) {
        emailListString = "<u>List of Emails</u><br>";
        listAsString(emailSet);
    }

    private void listAsString(Set<String> emailSet) {
        for (String email : emailSet) {
            emailListString += "<br>" + email.substring(0, email.length() - 4);
        }
    }

    public String toString() {
        return emailListString;
    }
}
