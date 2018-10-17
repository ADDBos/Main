package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BudgetBook;
import seedu.address.model.cca.Cca;

/**
 * A utility class containing a list of {@code Cca} objects to be used in tests.
 */
//@@author javenseow
//@author ericyjw
public class TypicalCcas {
    public static final Cca TRACK =
        new CcaBuilder().withCcaName("track").withHead(ALICE.getName()).withViceHead(DANIEL.getName())
            .withBudget(500).withSpent(100).withOutstanding(400).withTransaction("Spent on energy drinks/$100").build();
    public static final Cca BADMINTON =
        new CcaBuilder().withCcaName("badminton").withHead(BENSON.getName()).withViceHead(CARL.getName())
            .withBudget(500).withSpent(300).withOutstanding(100).withTransaction("Spent on equipments/$200\nSpent on "
            + "welfare/$100\n").build();

    private TypicalCcas() {} // prevents instantiation

    /**
     * Returns a {@code BudgetBook} with all the typical ccas.
     */
    public static BudgetBook getTypicalBudgetBook() {
        BudgetBook bb = new BudgetBook();
        for (Cca cca : getTypicalCcas()) {
            bb.addCca(cca);
        }
        return bb;
    }

    public static List<Cca> getTypicalCcas() {
        return new ArrayList<>(Arrays.asList(TRACK, BADMINTON));
    }
}
