package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author javenseow
public class FieldsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldsContainsKeywordsPredicate firstPredicate = new FieldsContainsKeywordsPredicate(firstPredicateKeywordList);
        FieldsContainsKeywordsPredicate secondPredicate =
                new FieldsContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        FieldsContainsKeywordsPredicate firstPredicateCopy =
                new FieldsContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> return false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_fieldsContainsKeywords_returnTrue() {
        // One keyword
        FieldsContainsKeywordsPredicate predicate = new FieldsContainsKeywordsPredicate(Collections
                .singletonList("soccer"));
        assertTrue(predicate.test(new PersonBuilder().withTags("soccer").build()));

        // Multiple keywords
        predicate = new FieldsContainsKeywordsPredicate(Arrays.asList("soccer", "track"));
        assertTrue(predicate.test(new PersonBuilder().withTags("soccer", "track").build()));

        // Only one matching keyword
        predicate = new FieldsContainsKeywordsPredicate(Arrays.asList("track", "badminton"));
        assertTrue(predicate.test(new PersonBuilder().withTags("soccer", "track").build()));

        // Mixed-case keywords
        predicate = new FieldsContainsKeywordsPredicate(Arrays.asList("soCcer", "TraCk"));
        assertTrue(predicate.test(new PersonBuilder().withTags("soccer", "track").build()));
    }

    @Test
    public void test_fieldsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldsContainsKeywordsPredicate predicate = new FieldsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("soccer").build()));

        // Non-matching keyword
        predicate = new FieldsContainsKeywordsPredicate(Arrays.asList("badminton"));
        assertFalse(predicate.test(new PersonBuilder().withTags("soccer", "track").build()));

        // Keywords match name, phone, email, but does not match other fields
        predicate = new FieldsContainsKeywordsPredicate(Arrays.asList("Alice", "alice@email.com", "987654"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("987654")
                .withEmail("alice@email.com").withTags("soccer").build()));
    }
}
