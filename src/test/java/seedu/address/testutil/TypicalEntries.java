package seedu.address.testutil;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.model.transaction.Entry;

//@author ericyjw

/**
 * A utility class containing a list of {@code Entries} objects to be used in tests.
 */
public class TypicalEntries {
    public static final Entry ENTRY_COMPETITION_1 =
        new TransactionEntryBuilder()
            .withEntryNum(1)
            .withDate("01.02.2018")
            .withAmount(-100)
            .withRemarks("Competition Fee")
            .build();
    public static final Entry ENTRY_COACH_2 =
        new TransactionEntryBuilder()
            .withEntryNum(2)
            .withDate("03.04.2018")
            .withAmount(-300)
            .withRemarks("Coaching Fee")
            .build();
    public static final Entry ENTRY_WELFARE_3 =
        new TransactionEntryBuilder()
            .withEntryNum(3)
            .withDate("30.12.2018")
            .withAmount(-50)
            .withRemarks("Welfare")
            .build();
    public static final Entry ENTRY_FUND_RAISING_4 =
        new TransactionEntryBuilder()
            .withEntryNum(4)
            .withDate("13.06.2018")
            .withAmount(150)
            .withRemarks("Fund Raising")
            .build();

    public static final Entry ENTRY_EQUIPMENT_1 =
        new TransactionEntryBuilder()
            .withEntryNum(1)
            .withDate("14.03.2018")
            .withAmount(-200)
            .withRemarks("Purchase of Equipment")
            .build();
    public static final Entry ENTRY_PRIZE_2 =
        new TransactionEntryBuilder()
            .withEntryNum(2)
            .withDate("03.11.2018")
            .withAmount(300)
            .withRemarks("Prize Money from Competition")
            .build();

    // Spent: 300 Outstanding: INITIAL BUDGET - 300
    public static final Set<Entry> TRANSACTION_4_ENTRIES = new LinkedHashSet<>(
        Arrays.asList(ENTRY_COMPETITION_1, ENTRY_COACH_2, ENTRY_WELFARE_3, ENTRY_FUND_RAISING_4));

    // Spent: 0 Outstanding: INITIAL BUDGET + 100
    public static final Set<Entry> TRANSACTION_2_ENTRIES = new LinkedHashSet<>(
        Arrays.asList(ENTRY_EQUIPMENT_1, ENTRY_PRIZE_2));

    // Spent: 0 Outstanding: INITIAL BUDGET
    public static final Set<Entry> TRANSACTION_EMPTY = new LinkedHashSet<>();

    private TypicalEntries() {
    } // prevents instantiation
}
