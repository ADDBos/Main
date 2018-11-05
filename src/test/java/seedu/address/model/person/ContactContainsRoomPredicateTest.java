package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author kengwoon
public class ContactContainsRoomPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContactContainsRoomPredicate firstPredicate = new ContactContainsRoomPredicate(firstPredicateKeywordList);
        ContactContainsRoomPredicate secondPredicate =
            new ContactContainsRoomPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> returns true
        ContactContainsRoomPredicate firstPredicateCopy =
            new ContactContainsRoomPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> return false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactContainsRoom_returnTrue() {
        // One room
        ContactContainsRoomPredicate predicate = new ContactContainsRoomPredicate(Collections
            .singletonList("A123"));
        assertTrue(predicate.test(new PersonBuilder().withRoom("A123").build()));

        // Multiple rooms
        predicate = new ContactContainsRoomPredicate(Arrays.asList("A123", "B456"));
        assertTrue(predicate.test(new PersonBuilder().withRoom("B456").build()));

        // Mixed-case rooms
        predicate = new ContactContainsRoomPredicate(Arrays.asList("a123"));
        assertTrue(predicate.test(new PersonBuilder().withRoom("A123").build()));
    }

    @Test
    public void test_contactsDoNotContainRoom_returnsFalse() {
        // Zero rooms
        ContactContainsRoomPredicate predicate = new ContactContainsRoomPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("A123").build()));

        // Non-matching room
        predicate = new ContactContainsRoomPredicate(Arrays.asList("A123"));
        assertFalse(predicate.test(new PersonBuilder().withRoom("B456").build()));
    }
}
