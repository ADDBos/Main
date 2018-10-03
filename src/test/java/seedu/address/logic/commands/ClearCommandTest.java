package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;

//@@author kengwoon
public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();
        List<String> target = new ArrayList<>();

        assertCommandSuccess(new ClearCommand(target), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEAR_SPECIFIC_SUCCESS, target.get(0)), expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.resetData(new AddressBook());
        expectedModel.commitAddressBook();
        List<String> target = new ArrayList<>();

        assertCommandSuccess(new ClearCommand(target), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEAR_SPECIFIC_SUCCESS, target.get(0)), expectedModel);
    }

    @Test
    public void execute_clearSpecific_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<String> target = new ArrayList<>();
        Object[] tags = p.getTags().toArray();
        target.add(tags[0].toString());
        ClearCommand clearCommand = new ClearCommand(target);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs());
        List<Person> persons = new ArrayList<>();
        persons.add(p);
        expectedModel.clearMultiplePersons(persons);
        expectedModel.commitAddressBook();

        assertCommandSuccess(clearCommand, model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEAR_SPECIFIC_SUCCESS, target.get(0)), expectedModel);
    }

}
