//@@author javenseow
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Tag} matches the keyword given.
 */
//@@author javenseow
public class FieldsContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public FieldsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsFieldIgnoreCase
                        (person.getFields().toString().replaceAll("\\[", "").replaceAll("]", "")
                        , keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FieldsContainsKeywordsPredicate //instanceof handles null
            && keywords.equals(((FieldsContainsKeywordsPredicate) other).keywords)); //state check
    }
}
