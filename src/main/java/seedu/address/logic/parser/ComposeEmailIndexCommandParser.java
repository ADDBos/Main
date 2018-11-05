//@@author EatOrBeEaten

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Set;
import java.util.stream.Stream;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ComposeEmailIndexCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ComposeEmailIndexCommand object
 */
public class ComposeEmailIndexCommandParser implements Parser<ComposeEmailIndexCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ComposeEmailIndexCommand
     * and returns an ComposeEmailIndexCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ComposeEmailIndexCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FROM, PREFIX_TO, PREFIX_SUBJECT, PREFIX_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_FROM, PREFIX_TO, PREFIX_SUBJECT, PREFIX_CONTENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ComposeEmailIndexCommand.MESSAGE_USAGE));
        }

        String from = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_FROM).get()).toString();
        Set<Index> indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_TO).get());
        String subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get()).toString();
        String content = ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT).get()).toString();

        Email email = EmailBuilder.startingBlank()
                .from(from)
                .withSubject(subject)
                .withHTMLText(content)
                .buildEmail();

        return new ComposeEmailIndexCommand(email, indexList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
