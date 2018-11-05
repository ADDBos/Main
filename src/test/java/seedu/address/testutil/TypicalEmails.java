package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_CAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_EXCURSION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simplejavamail.email.Email;

//@@author EatOrBeEaten

/**
 * A utility class containing a list of {@code org.simplejavamail.email.Email} objects to be used in tests.
 */
public class TypicalEmails {

    public static final Email MEETING_EMAIL = new DefaultEmailBuilder().withFrom("alice@example.com")
        .withTo("benson@example.com").withSubject("Meeting").withContent("We have a meeting on Friday").build();

    public static final Email CONFERENCE_EMAIL = new DefaultEmailBuilder().withFrom("benson@example.com")
        .withTo("carl@example.com").withSubject("Conference")
        .withContent("We have a conference on Saturday").build();

    public static final Email OUTING_EMAIL = new DefaultEmailBuilder().withFrom("carl@example.com")
        .withTo("daniel@example.com").withSubject("Outing").withContent("We have an outing on Sunday").build();

    // Manually added
    public static final Email EXCURSION_EMAIL = new DefaultEmailBuilder().withFrom(VALID_EMAIL_EXCURSION)
        .withTo(VALID_EMAIL_CAMP).withSubject(VALID_SUBJECT_EXCURSION)
        .withContent(VALID_CONTENT_EXCURSION).build();

    public static final Email EXCURSION_EMAIL_WITHOUT_TO = new DefaultEmailBuilder().withFrom(VALID_EMAIL_EXCURSION)
        .withTo(VALID_EMAIL_CAMP).withSubject(VALID_SUBJECT_EXCURSION).withContent(VALID_CONTENT_EXCURSION)
        .buildWithoutTo();

    public static final Email CAMP_EMAIL = new DefaultEmailBuilder().withFrom(VALID_EMAIL_CAMP)
        .withTo(VALID_EMAIL_EXCURSION).withSubject(VALID_SUBJECT_CAMP)
        .withContent(VALID_CONTENT_CAMP).build();

    public static final Email CAMP_EMAIL_WITHOUT_TO = new DefaultEmailBuilder().withFrom(VALID_EMAIL_CAMP)
        .withTo(VALID_EMAIL_EXCURSION).withSubject(VALID_SUBJECT_CAMP).withContent(VALID_CONTENT_CAMP)
        .buildWithoutTo();

    // prevents instantiation
    private TypicalEmails() {
    }

    /**
     * Returns a set of strings of all the typical email file names.
     */
    public static Set<String> getTypicalExistingEmails() {
        Set<String> subjectSet = new HashSet<>();
        for (Email email : getTypicalEmails()) {
            subjectSet.add(email.getSubject() + ".eml");
        }
        return subjectSet;
    }

    public static List<Email> getTypicalEmails() {
        return new ArrayList<>(Arrays.asList(MEETING_EMAIL, CONFERENCE_EMAIL, OUTING_EMAIL));
    }

}
