package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

//@@author kengwoon
/**
 * Clears the address book.
 */
public class EraseCommand extends Command {

    public static final String COMMAND_WORD = "erase";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Erases specified CCA "
            + " tag(s) from all persons in Hallper\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " basketball";
    public static final String MESSAGE_ERASE_SUCCESS = "Erased %1$s from persons in Hallper";
    public static final String MESSAGE_NOTHING_ERASED = "No persons under %1$s";

    private final ContactContainsTagPredicate predicate;

    private final List<String> target;
    private ArrayList<Person> toErase;
    private ArrayList<Person> modifiedPersons;
    private List<Person> fullList;
    private Set<Tag> tags;
    private Person temp;

    public EraseCommand(List<String> target) {
        this.target = target;
        this.predicate = new ContactContainsTagPredicate(target);
        this.toErase = new ArrayList<>();
        this.modifiedPersons = new ArrayList<>();
        this.fullList = new ArrayList<>();
        this.tags = new HashSet<>();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        toErase.clear();
        modifiedPersons.clear();
        fullList = model.getAddressBook().getPersonList();

        for (Person p : fullList) {
            if (new ContactContainsTagPredicate(target).test(p)) {
                toErase.add(p);
            }
        }

        for (Person p : toErase) {
            tags.clear();
            for (Tag t : p.getTags()) {
                tags.add(t);
                for (String tag : target) {
                    if (t.toStringOnly().equals(tag)) {
                        tags.remove(t);
                        break;
                    }
                }
            }
            temp = new Person(p.getName(), p.getPhone(), p.getEmail(), p.getRoom(), p.getSchool(), tags);
            modifiedPersons.add(temp);
        }

        if (modifiedPersons.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NOTHING_ERASED, target));
        }

        model.removeTagsFromPersons(modifiedPersons, toErase);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ERASE_SUCCESS, target));
    }
}
