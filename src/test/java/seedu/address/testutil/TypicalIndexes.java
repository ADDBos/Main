package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);
    public static final Index[] INDEX_ARRAY = {Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3)};
    public static final Set<Index> INDEX_SET = Set.of(INDEX_ARRAY);
}
