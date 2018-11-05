package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_missingPrefix_throwsParseException() {

        // no dst/ prefix
        assertParseFailure(parser, "C://path fn/file.xml", MESSAGE_INVALID_FORMAT);

        // no fn/ prefix
        assertParseFailure(parser, "dst/C://path file.xml", MESSAGE_INVALID_FORMAT);

        // no dst/ and fn/ prefix
        assertParseFailure(parser, "C://path file.xml", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_notXmlFile_throwsParseException() {

        // no '.xml' for filename
        assertParseFailure(parser, "dst/C://path fn/file", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsExportCommand() {

        // valid filepath and filename
        ExportCommand expectedExportCommand =
            new ExportCommand(new File("C://path/file.xml").toPath());
        assertParseSuccess(parser, " dst/C://path fn/file.xml", expectedExportCommand);
    }

}
