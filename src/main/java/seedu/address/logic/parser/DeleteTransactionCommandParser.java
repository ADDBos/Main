package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;

import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.logic.commands.DeleteTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
/**
 * Parses input arguments and creates a new DeleteTransactionCommand object
 *
 * @author ericyjw
 */
public class DeleteTransactionCommandParser implements Parser<DeleteTransactionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTransactionCommand
     * and returns an DeleteTransactionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_TRANSACTION);

        if (!argMultimap.getValue(PREFIX_TAG).isPresent() || !argMultimap.getValue(PREFIX_TRANSACTION).isPresent()) {
            throw new ParseException(DeleteTransactionCommand.MESSAGE_USAGE);
        }
        if (argMultimap.getAllValues(PREFIX_TAG).size() > 1
            || argMultimap.getAllValues(PREFIX_TRANSACTION).size() > 1) {
            throw new ParseException(DeleteTransactionCommand.MESSAGE_USAGE);
        }

        String name = argMultimap.getValue(PREFIX_TAG).get();
        int index = Integer.valueOf(argMultimap.getValue(PREFIX_TRANSACTION).get());


        if (!CcaName.isValidCcaName(name)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));
        }

        CcaName ccaName = new CcaName(argMultimap.getValue(PREFIX_TAG).get());

        return new DeleteTransactionCommand(ccaName, index);
    }
}
