package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.person.FieldsContainsKeywordsPredicate;


//@@author javenseow
public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new FieldsContainsKeywordsPredicate(Arrays.asList("soccer", "choir")));
        assertParseSuccess(parser, "soccer choir", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n soccer \n \t choir \t", expectedSearchCommand);
    }
}
