package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

//@@author kengwoon
/**
 * Tests that a {@code Person}'s {@code Tag} matches the keyword given.
 */
public class ContactContainsTagPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContactContainsTagPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();
        for (String s : keywords) {
            for (Tag t : tags) {
                if (s.equalsIgnoreCase(t.toStringOnly().toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsTagPredicate // instanceof handles nulls
                && keywords.equals(((ContactContainsTagPredicate) other).keywords)); // state check
    }

}
