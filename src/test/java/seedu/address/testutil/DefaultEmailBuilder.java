package seedu.address.testutil;

import org.simplejavamail.email.EmailBuilder;

import seedu.address.model.email.Content;
import seedu.address.model.email.Subject;
import seedu.address.model.person.Email;

//@@author EatOrBeEaten
/**
 * A utility class to help with building org.simplejavamail.email.Email objects.
 */
public class DefaultEmailBuilder {

    public static final String DEFAULT_FROM = "alice@gmail.com";
    public static final String DEFAULT_TO = "billy@gmail.com";
    public static final String DEFAULT_SUBJECT = "Meeting on Friday";
    public static final String DEFAULT_CONTENT = "Dear Billy<br /><br />See you tomorrow!<br /><br />Alice";

    private Email from;
    private Email to;
    private Subject subject;
    private Content content;

    public DefaultEmailBuilder() {
        from = new Email(DEFAULT_FROM);
        to = new Email(DEFAULT_TO);
        subject = new Subject(DEFAULT_SUBJECT);
        content = new Content(DEFAULT_CONTENT);
    }

    /**
     * Sets the {@code Subject} of the {@code Email} that we are building.
     */
    public DefaultEmailBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Returns an Email object built.
     * @return org.simplejavamail.email.Email object with builder values.
     */
    public org.simplejavamail.email.Email build() {
        return EmailBuilder.startingBlank()
                .from(from.value)
                .to(to.value)
                .withSubject(subject.value)
                .withHTMLText(content.value)
                .buildEmail();
    }
}
