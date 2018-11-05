package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

//@@author kengwoon

/**
 * Contains unit tests for ImportCommand.
 */
public class ImportCommandTest {

    private static final String VALID_PERSON_NAME = "John";
    private static final String VALID_PERSON_PHONE = "12345678";
    private static final String VALID_PERSON_EMAIL = "john@gmail.com";
    private static final String VALID_PERSON_ROOM = "C234";
    private static final String VALID_PERSON_SCHOOL = "Computing";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nullFile_throwsFileNotFoundException() {
        ImportCommand importCommand = new ImportCommand(new File("./imports/notAFile.xml").toPath());

        String expectedMessage = ImportCommand.MESSAGE_FILE_NOT_FOUND;

        assertCommandFailure(importCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidFileFormat() {
        ImportCommand importCommand = new ImportCommand(
            new File("./src/test/data/ImportCommandTest/invalidFormat.xml").toPath());

        String expectedMessage = ImportCommand.MESSAGE_PARSE_ERR;

        assertCommandFailure(importCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validContactsFile_success() {
        String fileName = "contactImports.xml";
        File file = new File("./src/test/data/ImportCommandTest/" + fileName);
        ImportCommand importCommand = new ImportCommand(file.toPath());

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("basketball"));
        Person newPerson = new Person(new Name(VALID_PERSON_NAME), new Phone(VALID_PERSON_PHONE),
                new Email(VALID_PERSON_EMAIL),
                new Room(VALID_PERSON_ROOM), new School(VALID_PERSON_SCHOOL), tags);
        expectedModel.addPerson(newPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCcaFile_success() {
        String fileName = "ccaImports.xml";
        File file = new File("./src/test/data/ImportCommandTest/" + fileName);
        ImportCommand importCommand = new ImportCommand(file.toPath());

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person original = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        PersonBuilder personInFile = new PersonBuilder(original);
        Person edited = personInFile.withTags("Golf").build();
        expectedModel.updatePerson(original, edited);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.commitAddressBook();

        assertCommandSuccess(importCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
