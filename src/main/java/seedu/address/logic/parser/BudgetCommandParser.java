package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.BudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaName;

//@author ericyjw
/**
 * Parses input arguments and creates a new BudgetCommand object
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand
     * and returns a BudgetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!argMultimap.getValue(PREFIX_TAG).isPresent() && !args.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        } else if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            return new BudgetCommand();
        }

        String ccaName = argMultimap.getValue(PREFIX_TAG).get();

        if (!CcaName.isValidCcaName(ccaName)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        }

        return new BudgetCommand(new CcaName(ccaName));
    }
}
