package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Remarks;

//@author ericyjw
/**
 * Parses input arguments and creates a new AddTransactionCommand object
 *
 * @author ericyjw
 */
public class AddTransactionCommandPaser implements Parser<AddTransactionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTransactionCommand
     * and returns an AddTransactionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_DATE, PREFIX_AMOUNT, PREFIX_REMARKS);

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException(AddTransactionCommand.MESSAGE_NO_SPECIFIC_CCA + "\n"
                + AddTransactionCommand.MESSAGE_USAGE);
        }

        if (!argMultimap.getValue(PREFIX_DATE).isPresent() || !argMultimap.getValue(PREFIX_AMOUNT).isPresent()
            || !argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            throw new ParseException(AddTransactionCommand.MESSAGE_NOT_UPDATED
                + "\n" + AddTransactionCommand.MESSAGE_USAGE);
        }

        if (argMultimap.getAllValues(PREFIX_TAG).size() > 1 || argMultimap.getAllValues(PREFIX_DATE).size() > 1
            || argMultimap.getAllValues(PREFIX_AMOUNT).size() > 1
            || argMultimap.getAllValues(PREFIX_REMARKS).size() > 1) {
            throw new ParseException(UpdateCommand.MESSAGE_USAGE);
        }

        CcaName ccaName = ParserUtil.parseCcaName((argMultimap.getValue(PREFIX_TAG).get()));
        Date date = ParserUtil.parseEntryDate(argMultimap.getValue(PREFIX_DATE).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Remarks remarks = ParserUtil.parseRemarks(argMultimap.getValue(PREFIX_REMARKS).get());

        return new AddTransactionCommand(ccaName, date, amount, remarks);
    }
}
