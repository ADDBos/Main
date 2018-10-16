package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.FieldsContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.ui.FileChooser;


/**
 * Updates the profile picture of a person to the address book.
 */
//@@author javenseow
public class ImageCommand extends Command {

    public static final String COMMAND_WORD = "image";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads a profile picture to the resident of "
            + "the specified room.\n"
            + "Parameters: ROOM "
            + "Example: " + COMMAND_WORD + " A123";

    public static final String CANCEL = "Cancel option chosen.";
    public static final String MESSAGE_SUCCESS = "Profile picture uploaded for %1$s";
    public static final String MESSAGE_CANCEL = "No picture was uploaded.";
    public static final String MESSAGE_NO_SUCH_PERSON = "There is no resident occupying that room.";
    public static final String MESSAGE_DUPLICATE_UPLOAD = "This resident already has that profile picture.";

    private final Room number;

    private List<Person> fullList;
    private List<String> roomNumber;

    /**
     * Creates an ImageCommand to add to the specified {@code Person}
     */
    public ImageCommand(Room value) {
        number = value;
        roomNumber = new ArrayList<>();
        roomNumber.add(value.toString());
        fullList = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        fullList = model.getAddressBook().getPersonList();
        Person resident = null;

        for (Person p : fullList) {
            if (new FieldsContainsKeywordsPredicate(roomNumber).test(p)) {
                resident = p;
                break;
            }
        }

        if (resident == null) {
            throw new CommandException(MESSAGE_NO_SUCH_PERSON);
        }

        String toUpload = FileChooser.showDialog();
        if (toUpload.equals(CANCEL)) {
            return new CommandResult(MESSAGE_CANCEL);
        }

        EditPersonProfilePicture editPersonProfilePicture = new EditPersonProfilePicture();
        editPersonProfilePicture.setProfilePicture(new ProfilePicture(toUpload));

        Person editedPerson = createEditedProfilePicturePerson(resident, editPersonProfilePicture);

        if (!resident.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_UPLOAD);
        }

        model.updatePerson(resident, editedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, resident));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonProfilePicture}.
     */
    private static Person createEditedProfilePicturePerson(Person personToEdit,
                                                           EditPersonProfilePicture editPersonProfilePicture) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Room room = personToEdit.getRoom();
        School school = personToEdit.getSchool();
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, room, school, editPersonProfilePicture.getProfilePicture(), tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImageCommand // instanceof handles nulls
                && number.equals(((ImageCommand) other).number)); // state check
    }

    /**
     * Stores the {@code profilePicture} data path to edit the person with. If the field
     * value is non-empty, it will replace the {@code profilePicture} field value of the
     * person.
     */
    public static class EditPersonProfilePicture {
        private ProfilePicture profilePicture;

        public void setProfilePicture(ProfilePicture profilePicture) {
            this.profilePicture = profilePicture;
        }

        public ProfilePicture getProfilePicture() {
            return profilePicture;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonProfilePicture)) {
                return false;
            }

            // state check
            EditPersonProfilePicture e = (EditPersonProfilePicture) other;

            return getProfilePicture().equals(e.getProfilePicture());
        }
    }
}

