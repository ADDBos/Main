package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.imports.ImportAddressBook;
import seedu.address.logic.commands.imports.ImportCcaList;
import seedu.address.model.Model;

//@@author kengwoon
/**
 * Imports an XML file to update Hallper.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports an XML file to update Hallper.\n "
            + "Parameters: "
            + "import "
            + "f/C://Users/Documents/FILENAME.xml";

    public static final String MESSAGE_SUCCESS = "%1$s file read and database updated.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_EMPTY_FILE = "File is empty";
    public static final String MESSAGE_CONFIG_ERR = "Configuration error.";
    public static final String MESSAGE_PARSE_ERR = "Error parsing XML file.";

    public static final String IMPORT_ADDRESSBOOK = "addressbook";
    public static final String IMPORT_CCA_LIST = "list";
    public static final String IMPORT_BUDGETBOOK = "ccabook";
    public static final String IMPORT_TRANSACTION = "transactions";

    private final Path path;

    /**
     * Creates an ImportCommand to import the file in specified path.
     */
    public ImportCommand(Path path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Document doc = parseFile();

        String rootName = doc.getDocumentElement().getNodeName();
        switch (rootName) {
        case IMPORT_ADDRESSBOOK:
            new ImportAddressBook(doc, model).execute();
            break;
        case IMPORT_CCA_LIST:
            new ImportCcaList(doc, model).execute();
            break;
        default:
            throw new CommandException(MESSAGE_PARSE_ERR);
        }

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, path.getFileName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
            && path.equals(((ImportCommand) other).path));
    }

    /**
     * Parses XML file for reading.
     */
    private Document parseFile() throws CommandException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(path.toFile());
            doc.getDocumentElement().normalize();
            return doc;

        } catch (ParserConfigurationException e) {
            throw new CommandException(MESSAGE_CONFIG_ERR);
        } catch (SAXException e) {
            throw new CommandException(MESSAGE_PARSE_ERR);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE);
        }
    }
}
