package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_CAMP;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.FROM_DESC_CAMP;
import static seedu.address.logic.commands.CommandTestUtil.FROM_DESC_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FROM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_CAMP;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_EXCURSION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEmails.EXCURSION_EMAIL;

import org.junit.Test;
import org.simplejavamail.email.Email;

import seedu.address.logic.commands.ComposeEmailListCommand;
import seedu.address.model.email.Content;
import seedu.address.model.email.Subject;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class ComposeEmailListCommandParserTest {
    private ComposeEmailListCommandParser parser = new ComposeEmailListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Email expectedEmail = new DefaultEmailBuilder(EXCURSION_EMAIL).buildWithoutTo();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FROM_DESC_EXCURSION + SUBJECT_DESC_EXCURSION
            + CONTENT_DESC_EXCURSION, new ComposeEmailListCommand(expectedEmail));

        // multiple from - last from accepted
        assertParseSuccess(parser, FROM_DESC_CAMP + FROM_DESC_EXCURSION + SUBJECT_DESC_EXCURSION
            + CONTENT_DESC_EXCURSION, new ComposeEmailListCommand(expectedEmail));

        // multiple subject - last subject accepted
        assertParseSuccess(parser, FROM_DESC_EXCURSION + SUBJECT_DESC_CAMP + SUBJECT_DESC_EXCURSION
            + CONTENT_DESC_EXCURSION, new ComposeEmailListCommand(expectedEmail));

        // multiple content - last content accepted
        assertParseSuccess(parser, FROM_DESC_EXCURSION + SUBJECT_DESC_EXCURSION + CONTENT_DESC_CAMP
            + CONTENT_DESC_EXCURSION, new ComposeEmailListCommand(expectedEmail));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ComposeEmailListCommand.MESSAGE_USAGE);

        // missing from prefix
        assertParseFailure(parser, VALID_EMAIL_EXCURSION + SUBJECT_DESC_EXCURSION + CONTENT_DESC_EXCURSION,
            expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, FROM_DESC_EXCURSION + VALID_SUBJECT_EXCURSION + CONTENT_DESC_EXCURSION,
            expectedMessage);

        // missing content prefix
        assertParseFailure(parser, FROM_DESC_EXCURSION + SUBJECT_DESC_EXCURSION + VALID_CONTENT_EXCURSION,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EMAIL_EXCURSION + VALID_SUBJECT_EXCURSION + VALID_CONTENT_EXCURSION,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid from
        assertParseFailure(parser, INVALID_FROM_DESC + SUBJECT_DESC_EXCURSION + CONTENT_DESC_EXCURSION,
            seedu.address.model.person.Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, FROM_DESC_EXCURSION + INVALID_SUBJECT_DESC + CONTENT_DESC_EXCURSION,
            Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        // invalid content
        assertParseFailure(parser, FROM_DESC_EXCURSION + SUBJECT_DESC_EXCURSION + INVALID_CONTENT_DESC,
            Content.MESSAGE_CONTENT_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, FROM_DESC_EXCURSION + INVALID_SUBJECT_DESC + INVALID_CONTENT_DESC,
            Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FROM_DESC_EXCURSION
                + SUBJECT_DESC_EXCURSION + CONTENT_DESC_EXCURSION,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ComposeEmailListCommand.MESSAGE_USAGE));
    }
}
