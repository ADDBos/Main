package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author kengwoon
public class ContactContainsTagPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContactContainsTagPredicate firstPredicate = new ContactContainsTagPredicate(firstPredicateKeywordList);
        ContactContainsTagPredicate secondPredicate = new ContactContainsTagPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        ContactContainsTagPredicate firstPredicateCopy =
            new ContactContainsTagPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactContainsTags_returnTrue() {
        // One tag
        ContactContainsTagPredicate predicate = new ContactContainsTagPredicate(Collections
            .singletonList("Basketball"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Basketball").build()));

        // Multiple tags
        predicate = new ContactContainsTagPredicate(Arrays.asList("Basketball", "Golf"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Basketball", "Golf").build()));

        // Only one matching tags
        predicate = new ContactContainsTagPredicate(Arrays.asList("Golf", "Swimming"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Basketball", "Golf").build()));

        // Mixed-case tags
        predicate = new ContactContainsTagPredicate(Arrays.asList("BasKEtBall", "goLf"));
        assertTrue(predicate.test(new PersonBuilder().withTags("basketball", "golf").build()));
    }

    @Test
    public void test_contactsDoNotContainTags_returnsFalse() {
        // Zero tags
        FieldsContainsKeywordsPredicate predicate = new FieldsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Basketball").build()));

        // Non-matching tags
        predicate = new FieldsContainsKeywordsPredicate(Arrays.asList("Swimming"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Basketball", "Golf").build()));

        // Tags match name, phone, email, but do not match other fields
        predicate = new FieldsContainsKeywordsPredicate(Arrays.asList("Alice", "alice@email.com", "987654"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("987654")
            .withEmail("alice@email.com").withTags("Basketball").build()));
    }

}
