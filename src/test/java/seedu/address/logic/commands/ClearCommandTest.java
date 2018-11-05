package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_emptyAddressBook_success() {
        Model modelEmpty = new ModelManager();
        Model expectedModelEmpty = new ModelManager();
        List<String> target = new ArrayList<>();
        target.add("Basketball");
        ClearCommand clearCommand = new ClearCommand(target);

        String expectedMessage = String.format(ClearCommand.MESSAGE_CLEAR_NOTHING, '[' + target.get(0) + ']');

        assertCommandSuccess(clearCommand, modelEmpty, commandHistory, expectedMessage, expectedModelEmpty);
    }

    @Test
    public void execute_clearAll_success() {
        List<String> target = new ArrayList<>();
        target.add("all");
        ClearCommand clearCommand = new ClearCommand(target);

        String expectedMessage = ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(model.getBudgetBook()), new UserPrefs(), model.getExistingEmails());
        expectedModel.resetData(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(clearCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearSpecific_success() {
        List<String> target = new ArrayList<>();
        target.add("Handball");
        ClearCommand clearCommand = new ClearCommand(target);

        String expectedMessage = String.format(ClearCommand.MESSAGE_CLEAR_SPECIFIC_SUCCESS, '[' + target.get(0) + ']');

        Model expectedModel = new ModelManager(model.getAddressBook(),
            new BudgetBook(), new UserPrefs(), model.getExistingEmails());
        List<Person> fullList = model.getFilteredPersonList();
        List<Person> clear = new ArrayList<>();
        for (Person p : fullList) {
            if (new ContactContainsTagPredicate(target).test(p)) {
                clear.add(p);
            }
        }

        expectedModel.clearMultiplePersons(clear);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.commitAddressBook();

        assertCommandSuccess(clearCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}
