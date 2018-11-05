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
    public static final String DEFAULT_SUBJECT = "Example Subject";
    public static final String DEFAULT_CONTENT = "Dear Billy<br><br>See you tomorrow!<br><br>Alice";

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
     * Initialises the DefaultEmailBuilder with the data of {@code emailToCopy}.
     */
    public DefaultEmailBuilder(org.simplejavamail.email.Email emailToCopy) {
        from = new Email(emailToCopy.getFromRecipient().getAddress());
        to = new Email(emailToCopy.getRecipients().get(0).getAddress());
        subject = new Subject(emailToCopy.getSubject());
        content = new Content(emailToCopy.getHTMLText());
    }

    /**
     * Sets the {@code Subject} of the {@code Email} that we are building.
     */
    public DefaultEmailBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the 'from' of the {@code Email} that we are building.
     */
    public DefaultEmailBuilder withFrom(String from) {
        this.from = new Email(from);
        return this;
    }

    /**
     * Sets the 'to' of the {@code Email} that we are building.
     */
    public DefaultEmailBuilder withTo(String to) {
        this.to = new Email(to);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code Email} that we are building.
     */
    public DefaultEmailBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    /**
     * Returns an Email.
     */
    public org.simplejavamail.email.Email build() {
        return EmailBuilder.startingBlank()
                .from(from.value)
                .to(to.value)
                .withSubject(subject.value)
                .withHTMLText(content.value)
                .buildEmail();
    }

    /**
     * Returns an Email without recipients.
     */
    public org.simplejavamail.email.Email buildWithoutTo() {
        return EmailBuilder.startingBlank()
            .from(from.value)
            .withSubject(subject.value)
            .withHTMLText(content.value)
            .buildEmail();
    }
}
