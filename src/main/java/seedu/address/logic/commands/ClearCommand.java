package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsRoomPredicate;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Room;
import seedu.address.model.tag.Tag;

//@@author kengwoon
/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all persons associated with "
            + "the specified keywords(case-insensitive) or clears all persons for keyword 'all'.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " all\n"
            + "Example: " + COMMAND_WORD + " basketball\n"
            + "Example: " + COMMAND_WORD + "A123";
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "Hallper has been cleared!";
    public static final String MESSAGE_CLEAR_SPECIFIC_SUCCESS = "Cleared persons under %1$s in Hallper";
    public static final String MESSAGE_CLEAR_NOTHING = "No persons found under %1$s in Hallper";

    private final List<String> target;
    private boolean isClearAll;
    private boolean isClearRoom;
    private boolean isClearTag;

    public ClearCommand(List<String> target) {
        this.target = target;
        this.isClearAll = false;
        this.isClearRoom = false;
        this.isClearTag = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        for (String s : target) {
            if (s.toLowerCase().equals("all")) {
                this.isClearAll = true;
                break;
            }
            if (Room.isValidRoom(s)) {
                this.isClearRoom = true;
            }
            if (Tag.isValidTagName(s)) {
                this.isClearTag = true;
            }
        }

        if (isClearAll) {
            return clearAll(model);
        } else {
            return clearSpecific(model);
        }
    }

    /**
     * Clears data in entire address book.
     * @param model model component
     * @return CommandResult
     */
    private CommandResult clearAll(Model model) {
        model.resetData(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_CLEAR_ALL_SUCCESS);
    }

    /**
     * Clears data of specific tags.
     * @param model model component
     * @return CommandResult
     */
    private CommandResult clearSpecific(Model model) {
        ContactContainsTagPredicate predicateTag = new ContactContainsTagPredicate(target);
        ContactContainsRoomPredicate predicateRoom = new ContactContainsRoomPredicate(target);
        List<Person> toClear = new ArrayList<>();
        List<Person> fullList = model.getAddressBook().getPersonList();
        for (Person p : fullList) {
            if (isClearRoom && predicateRoom.test(p)) {
                toClear.add(p);
            }
            if (isClearTag && predicateTag.test(p)) {
                toClear.add(p);
            }
        }

        if (toClear.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_CLEAR_NOTHING, target));
        }

        model.clearMultiplePersons(toClear);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_CLEAR_SPECIFIC_SUCCESS, target));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClearCommand // instance of handles null
            && target.equals(((ClearCommand) other).target));
    }
}
