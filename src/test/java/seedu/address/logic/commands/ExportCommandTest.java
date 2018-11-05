package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.io.File;
import java.nio.file.Path;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExportCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Path path = new File("C://Users/Documents").toPath();
        Model modelEmpty = new ModelManager();
        Model expectedModelEmpty = new ModelManager();
        ExportCommand exportCommand = new ExportCommand(path);

        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, path);

        assertCommandSuccess(exportCommand, modelEmpty, commandHistory, expectedMessage, expectedModelEmpty);
    }
}
