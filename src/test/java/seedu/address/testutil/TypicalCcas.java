package seedu.address.testutil;

import static seedu.address.testutil.TypicalEntries.TRANSACTION_2_ENTRIES;
import static seedu.address.testutil.TypicalEntries.TRANSACTION_4_ENTRIES;
import static seedu.address.testutil.TypicalEntries.TRANSACTION_EMPTY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BudgetBook;
import seedu.address.model.cca.Cca;

//@@author javenseow ericyjw
/**
 * A utility class containing a list of {@code Cca} objects to be used in tests.
 */
public class TypicalCcas {
    public static final Cca BASKETBALL =
        new CcaBuilder()
            .withCcaName("BASKETBALL")
            .withHead("Steven Lim")
            .withViceHead("Benson")
            .withBudget(500)
            .withSpent(0)
            .withOutstanding(0)
            .build();

    public static final Cca TRACK =
        new CcaBuilder()
            .withCcaName("track")
            .withHead("Alice")
            .withViceHead("Grace")
            .withBudget(500)
            .withSpent(300)
            .withOutstanding(200)
            .withTransaction(TRANSACTION_4_ENTRIES)
            .build();
    public static final Cca BADMINTON =
        new CcaBuilder()
            .withCcaName("badminton")
            .withHead("BENSON")
            .withViceHead("Carls")
            .withBudget(500)
            .withSpent(0)
            .withOutstanding(600)
            .withTransaction(TRANSACTION_2_ENTRIES)
            .build();
    public static final Cca FLOORBALL =
        new CcaBuilder()
            .withCcaName("FLOORBALL")
            .withHead("-")
            .withViceHead("-")
            .withBudget(500)
            .withSpent(0)
            .withOutstanding(0)
            .withTransaction(TRANSACTION_EMPTY)
            .build();


    private TypicalCcas() {} // prevents instantiation

    /**
     * Returns a {@code BudgetBook} with all the typical Ccas.
     */
    public static BudgetBook getTypicalBudgetBook() {
        BudgetBook bb = new BudgetBook();
        for (Cca cca : getTypicalCcas()) {
            bb.addCca(cca);
        }
        return bb;
    }

    public static List<Cca> getTypicalCcas() {
        return new ArrayList<>(Arrays.asList(TRACK, BADMINTON, BASKETBALL));
    }
}
