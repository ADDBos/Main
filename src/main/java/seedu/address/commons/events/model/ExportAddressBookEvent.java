package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author kengwoon

/**
 * Indicates that the contacts in Hallper are being exported.
 */
public class ExportAddressBookEvent extends BaseEvent {

    private Path path;
    private ReadOnlyAddressBook addressBook;

    public ExportAddressBookEvent(ReadOnlyAddressBook addressBook, Path path) {
        this.addressBook = addressBook;
        this.path = path;
    }

    public Path getPath() {
        return this.path;
    }

    public ReadOnlyAddressBook getAddressBook() {
        return this.addressBook;
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
