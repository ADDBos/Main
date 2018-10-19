package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

//@@author EatOrBeEaten
/**
 * Composes an email to all currently listed people.
 */
public class EmailListCommand extends Command {

    public static final String COMMAND_WORD = "email_all";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Composes an email to all currently listed people. "
            + "Parameters: "
            + PREFIX_FROM + "EMAIL "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_CONTENT + "CONTENT(Input <br /> for newline)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FROM + "johndoe@example.com "
            + PREFIX_SUBJECT + "Meeting this Friday "
            + PREFIX_CONTENT + "Dear All<br /><br />Remember our meeting this friday.<br /><br />John";

    public static final String MESSAGE_SUCCESS = "Email(All) composed: %s";

    private final Email toCompose;

    public EmailListCommand(Email email) {
        toCompose = email;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Email emailWithRecipient = addRecipientsToEmail(lastShownList);
        model.saveEmail(emailWithRecipient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, emailWithRecipient.getSubject()));
    }

    /**
     * Creates an {@code Email} with recipients in the current list.
     * @param lastShownList Current filtered list.
     * @return Email with recipients from list.
     */
    private Email addRecipientsToEmail(List<Person> lastShownList) {
        final Set<String> emailList = new HashSet<>();
        for (Person person : lastShownList) {
            emailList.add(person.getEmail().value);
        }
        return EmailBuilder.copying(toCompose).toMultiple(emailList).buildEmail();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailListCommand // instanceof handles nulls
                && toCompose.equals(((EmailListCommand) other).toCompose));
    }
}
