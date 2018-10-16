package seedu.address.model;

import java.util.Iterator;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;

//@@author EatOrBeEaten
/**
 * Wraps Email data.
 */
public class EmailModel {

    private Email email;
    private String preview;

    private final String previewHeader = "<u>Email Preview</u><br /><br />";

    /**
     * Saves email to EmailModel.
     * @param email The email to be saved.
     */
    public void saveEmail(Email email) {
        this.email = email;
        savePreview();
    }

    /**
     * Creates preview of email in EmailModel.
     */
    private void savePreview() {
        String from = "From: " + email.getFromRecipient().getAddress();
        Iterator<Recipient> itr = email.getRecipients().iterator();
        String to = "To: " + itr.next().getAddress();
        String subject = "Subject: " + email.getSubject();
        while (itr.hasNext()) {
            to = to + ", " + itr.next().getAddress();
        }
        preview = previewHeader + from + "<br />" + to + "<br />" + subject + "<br /><br />" + email.getHTMLText();
    }

    public Email getEmail() {
        return email;
    }

    public String getPreview() {
        return preview;
    }
}
