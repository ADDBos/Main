package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

//@@author kengwoon
/**
 * Tests that a {@code Person}'s {@code Tag} matches the keyword given.
 */
public class ContactContainsRoomPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContactContainsRoomPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Room room = person.getRoom();
        for (String s : keywords) {
            if (s.toLowerCase().equals(room.toString().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsRoomPredicate // instanceof handles nulls
                && keywords.equals(((ContactContainsRoomPredicate) other).keywords)); // state check
    }

}
