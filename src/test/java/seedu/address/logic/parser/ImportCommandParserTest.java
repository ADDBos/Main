package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;

//@@author kengwoon
public class ImportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_missingPrefix_throwsParseException() {

        // no prefix
        assertParseFailure(parser, "C://path/file.xml", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_notXmlFile_throwsParseException() {

        // no '.xml' for filename
        assertParseFailure(parser, "f/C://path/file", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsImportCommand() {

        // valid path and filename
        ImportCommand expectedImportCommand =
            new ImportCommand(new File("C://path/file.xml").toPath());
        assertParseSuccess(parser, " f/C://path/file.xml", expectedImportCommand);
    }

}
