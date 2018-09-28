package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.FieldsContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose data fields match any of the argument keyword.
 * Keyword matching is case insensitive.
 */
// @@author javenseow
public class SearchCommand extends Command{
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are tagged with "
            + "any of the specified keywords (case insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " soccer";

    private final FieldsContainsKeywordsPredicate predicate;

    public SearchCommand(FieldsContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SearchCommand // instance of handles null
            && predicate.equals(((SearchCommand) other).predicate));
    }
}
