package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VICE_HEAD;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaName;

//@author ericyjw
/**
 * Parses input arguments and creates a new UpdateCommand object
 *
 * @author ericyjw
 */
public class UpdateCommandPaser implements Parser<UpdateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NAME, PREFIX_HEAD, PREFIX_VICE_HEAD, PREFIX_BUDGET,
                PREFIX_TRANSACTION, PREFIX_DATE, PREFIX_AMOUNT, PREFIX_REMARKS);

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException(UpdateCommand.MESSAGE_NO_SPECIFIC_CCA + "\n" + UpdateCommand.MESSAGE_USAGE);
        }
        CcaName ccaName = ParserUtil.parseCcaName((argMultimap.getValue(PREFIX_TAG).get()));

        EditCcaDescriptor editCcaDescriptor = new EditCcaDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCcaDescriptor.setCcaName(ParserUtil.parseCcaName((argMultimap.getValue(PREFIX_NAME).get())));
        }
        if (argMultimap.getValue(PREFIX_HEAD).isPresent()) {
            editCcaDescriptor.setHead(ParserUtil.parseName(argMultimap.getValue(PREFIX_HEAD).get()));
        }
        if (argMultimap.getValue(PREFIX_VICE_HEAD).isPresent()) {
            editCcaDescriptor.setViceHead(ParserUtil.parseName(argMultimap.getValue(PREFIX_VICE_HEAD).get()));
        }

        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editCcaDescriptor.setBudget(ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }

        if (argMultimap.getAllValues(PREFIX_TRANSACTION).size() > 1 || argMultimap.getAllValues(PREFIX_DATE).size() > 1
            || argMultimap.getAllValues(PREFIX_AMOUNT).size() > 1
            || argMultimap.getAllValues(PREFIX_REMARKS).size() > 1) {
            throw new ParseException(UpdateCommand.MESSAGE_USAGE);
        }

        if (argMultimap.getValue(PREFIX_TRANSACTION).isPresent()) {
            editCcaDescriptor.setEntryNum(ParserUtil.parseEntryNum(argMultimap.getValue(PREFIX_TRANSACTION).get())
            );
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editCcaDescriptor.setDate(ParserUtil.parseEntryDate(argMultimap.getValue(PREFIX_DATE).get())
            );
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editCcaDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get())
            );
        }
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            editCcaDescriptor.setRemarks(ParserUtil.parseRemarks(argMultimap.getValue(PREFIX_REMARKS).get())
            );
        }
        if (!editCcaDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(ccaName, editCcaDescriptor);
    }
}
