package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;

//@@author kengwoon
public class ClearCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE);

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {

        // no field specified
        assertParseFailure(parser, "   ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validAllArg_returnsClearCommand() {
        ClearCommand expectedClearCommand = new ClearCommand(Arrays.asList("all"));

        // 'all' argument without leading and trailing whitespaces
        assertParseSuccess(parser, "all", expectedClearCommand);

        // 'all' argument with leading and trailing whitespaces
        assertParseSuccess(parser, "  all  ", expectedClearCommand);
    }

    @Test
    public void parse_validArgs_returnsClearCommand() {
        ClearCommand expectedClearCommand = new ClearCommand(Arrays.asList("D111", "Basketball"));

        // valid room and cca without leading and trailing whitespaces
        assertParseSuccess(parser, "D111 Basketball", expectedClearCommand);

        // valid room and cca with leading and trailing whitespaces
        assertParseSuccess(parser, "   D111  Basketball     ", expectedClearCommand);
    }

}
