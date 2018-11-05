package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EraseCommand.MESSAGE_ERASE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

//@@author kengwoon
class EraseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    void execute_eraseTag_success() {
        List<String> target = new ArrayList<>();
        List<Person> original = new ArrayList<>();
        List<Person> erased = new ArrayList<>();
        String targetTag = "Basketball";
        target.add(targetTag);
        EraseCommand eraseCommand = new EraseCommand(target);

        String expectedMessage = String.format(MESSAGE_ERASE_SUCCESS, '[' + target.get(0) + ']');


        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new BudgetBook(), new UserPrefs(), model.getExistingEmails());
        List<Person> fullList = model.getFilteredPersonList();

        for (Person p : fullList) {
            if (new ContactContainsTagPredicate(target).test(p)) {
                ArrayList<String> editedTags = new ArrayList<>();
                //int index = 0;
                for (Tag t : p.getTags()) {
                    if (!t.toStringOnly().equals(targetTag)) {
                        editedTags.add(t.toStringOnly());
                    }
                }
                PersonBuilder personErase = new PersonBuilder(p);
                if (!editedTags.isEmpty()) {
                    String[] stringArray = editedTags.toArray(new String[0]);
                    Person editedPerson = personErase.withTags(stringArray).build();
                    original.add(p);
                    erased.add(editedPerson);
                } else {
                    Person editedPerson = personErase.withTags().build();
                    original.add(p);
                    erased.add(editedPerson);
                }
            }
        }

        expectedModel.removeTagsFromPersons(erased, original);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.commitAddressBook();

        assertCommandSuccess(eraseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}
