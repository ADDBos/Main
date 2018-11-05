package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
/**
 * Parses input arguments and creates a new DeleteCcaCommand object
 *
 * @author ericyjw
 */
public class DeleteCcaCommandParser implements Parser<DeleteCcaCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCcaCommand
     * and returns an DeleteCcaCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCcaCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        String name = argMultimap.getValue(PREFIX_TAG).get();

        if (!CcaName.isValidCcaName(name)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));
        }

        CcaName ccaName = new CcaName(argMultimap.getValue(PREFIX_TAG).get());

        return new DeleteCcaCommand(ccaName);
    }
}
