package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ROOM_AMY = "B124";
    public static final String VALID_ROOM_BOB = "A410";
    public static final String VALID_SCHOOL_AMY = "SDE";
    public static final String VALID_SCHOOL_BOB = "Biz";
    public static final String VALID_TAG_AMY = "Handball";
    public static final String VALID_TAG_BOB = "Floorball";

    public static final String VALID_EMAIL_EXCURSION = "excursion@example.com";
    public static final String VALID_EMAIL_CAMP = "camp@example.com";
    public static final String VALID_CONTENT_EXCURSION = "We have an excursion on Monday";
    public static final String VALID_CONTENT_CAMP = "We have a camp on Tuesday";
    public static final String VALID_SUBJECT_EXCURSION = "Excursion";
    public static final String VALID_SUBJECT_CAMP = "Camp";
    public static final String VALID_CONTENT_AMY = "Dear Bob<br /><br />See you tomorrow!<br /><br />Amy";
    public static final String VALID_CONTENT_BOB = "Dear Amy<br /><br />See you tomorrow!<br /><br />Bob";
    public static final String VALID_SUBJECT_AMY = "Meeting Tomorrow";
    public static final String VALID_SUBJECT_BOB = "Conference Tomorrow";
    public static final String VALID_CCA_NAME_BASKETBALL = "Basketball";
    public static final String VALID_CCA_NAME_BADMINTON = "BADMINTON";
    public static final String VALID_CCA_NAME_TRACK = "track";
    public static final String VALID_CCA_NAME_FLOORBALL = "FLOORBALL M";
    public static final String VALID_CCA_NAME_HOCKEY = "hockey f";
    public static final String VALID_BUDGET = "700";
    public static final String VALID_ENTRY_NUM = "1";
    public static final String VALID_DATE = "12.12.2018";
    public static final String VALID_AMOUNT = "-100";
    public static final String VALID_REMARKS = "Purchase of Equipment";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ROOM_DESC_AMY = " " + PREFIX_ROOM + VALID_ROOM_AMY;
    public static final String ROOM_DESC_BOB = " " + PREFIX_ROOM + VALID_ROOM_BOB;
    public static final String SCHOOL_DESC_AMY = " " + PREFIX_SCHOOL + VALID_SCHOOL_AMY;
    public static final String SCHOOL_DESC_BOB = " " + PREFIX_SCHOOL + VALID_SCHOOL_BOB;
    public static final String TAG_DESC_AMY = " " + PREFIX_TAG + VALID_TAG_AMY;
    public static final String TAG_DESC_BOB = " " + PREFIX_TAG + VALID_TAG_BOB;

    public static final String FROM_DESC_EXCURSION = " " + PREFIX_FROM + VALID_EMAIL_EXCURSION;
    public static final String FROM_DESC_CAMP = " " + PREFIX_FROM + VALID_EMAIL_CAMP;
    public static final String CONTENT_DESC_EXCURSION = " " + PREFIX_CONTENT + VALID_CONTENT_EXCURSION;
    public static final String CONTENT_DESC_CAMP = " " + PREFIX_CONTENT + VALID_CONTENT_CAMP;
    public static final String SUBJECT_DESC_EXCURSION = " " + PREFIX_SUBJECT + VALID_SUBJECT_EXCURSION;
    public static final String SUBJECT_DESC_CAMP = " " + PREFIX_SUBJECT + VALID_SUBJECT_CAMP;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ROOM_DESC = " " + PREFIX_ROOM; // empty string not allowed for room
    public static final String INVALID_SCHOOL_DESC = " " + PREFIX_SCHOOL
            + "School of Computing"; // spacing no allowed for school
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_FROM_DESC = " " + PREFIX_FROM + "excursion!yahoo"; // missing '@' symbol
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT; // empty string not allowed for subjects
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT; // empty string not allowed for contents

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY)
                .withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB)
                .withSchool(VALID_SCHOOL_BOB).withTags(VALID_TAG_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

}
