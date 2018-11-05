package seedu.address.model.cca;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BADMINTON;
import static seedu.address.testutil.TypicalCcas.BADMINTON;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalCcas.FLOORBALL;
import static seedu.address.testutil.TypicalCcas.TRACK;
import static seedu.address.testutil.TypicalEntries.TRANSACTION_2_ENTRIES;
import static seedu.address.testutil.TypicalEntries.TRANSACTION_4_ENTRIES;
import static seedu.address.testutil.TypicalEntries.TRANSACTION_EMPTY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.CcaBuilder;

//@@author ericyjw
/**
 * To test if 2 Ccas are the same.
 * To test if 2 Ccas are equals.
 */
public class CcaTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Cca cca = new CcaBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        cca.getEntries().remove(0);
    }

    @Test
    public void isSameCca() {
        // same object -> returns true
        assertTrue(BASKETBALL.isSameCca(BASKETBALL));

        // null -> returns false
        assertFalse(BASKETBALL.isSameCca(null));

        // different Cca object -> false
        assertFalse(BASKETBALL.isSameCca(TRACK));

        // different budget -> returns true
        Cca editedBadminton = new CcaBuilder(BADMINTON)
            .withBudget(700)
            .build();
        assertTrue(BADMINTON.isSameCca(editedBadminton));

        // different head and vice head -> return true
        Cca editedTrack = new CcaBuilder(TRACK)
            .withHead("Alice")
            .withViceHead("Bob")
            .build();
        assertTrue(TRACK.isSameCca(editedTrack));

        // different spent and outstanding -> return true
        Cca editedBasketball = new CcaBuilder(BASKETBALL)
            .withSpent(300)
            .withOutstanding(200)
            .build();
        assertTrue(BASKETBALL.isSameCca(editedBasketball));

        // different name -> returns false
        Cca editedFloorball = new CcaBuilder(FLOORBALL)
            .withCcaName("FLOORBALL M")
            .build();
        assertFalse(FLOORBALL.isSameCca(editedFloorball));

        // same name, different transactions -> returns true
        editedBadminton = new CcaBuilder(BADMINTON)
            .withBudget(700)
            .withTransaction(TRANSACTION_EMPTY)
            .build();
        assertTrue(BADMINTON.isSameCca(editedBadminton));

        // same name, same budget, different transaction -> returns true
        editedBasketball = new CcaBuilder(BASKETBALL)
            .withTransaction(TRANSACTION_2_ENTRIES)
            .build();
        assertTrue(BASKETBALL.isSameCca(editedBasketball));

        // same name, same budget, no head, no vice head, different transaction -> return true
        editedFloorball = new CcaBuilder(FLOORBALL)
            .withTransaction(TRANSACTION_2_ENTRIES)
            .build();
        assertTrue(FLOORBALL.isSameCca(editedFloorball));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Cca trackCopy = new CcaBuilder(TRACK).build();
        assertTrue(TRACK.equals(trackCopy));

        // same object -> returns true
        assertTrue(BASKETBALL.equals(BASKETBALL));

        // null -> returns false
        assertFalse(BADMINTON.equals(null));

        // different type -> returns false
        assertFalse(FLOORBALL.equals(5));

        // different Cca -> returns false
        assertFalse(BASKETBALL.equals(BADMINTON));

        // different name -> returns false
        Cca editedTrack = new CcaBuilder(TRACK)
            .withCcaName(VALID_CCA_NAME_BADMINTON)
            .build();
        assertFalse(TRACK.equals(editedTrack));

        // different budget -> returns false
        Cca editedBadminton = new CcaBuilder(BADMINTON)
            .withBudget(Integer.valueOf(VALID_BUDGET))
            .build();
        assertFalse(BADMINTON.equals(editedBadminton));

        // different head -> returns false
        Cca editedBasketball = new CcaBuilder(BASKETBALL)
            .withHead("Bob")
            .build();
        assertFalse(BASKETBALL.equals(editedBasketball));

        // different vice head -> returns false
        Cca editedFloorball = new CcaBuilder(FLOORBALL)
            .withViceHead("Alice")
            .build();
        assertFalse(FLOORBALL.equals(editedFloorball));

        // different budget -> return false
        editedTrack = new CcaBuilder(TRACK)
            .withBudget(Integer.valueOf(VALID_BUDGET))
            .build();
        assertFalse(TRACK.equals(editedTrack));

        // different spent -> return true
        editedBadminton = new CcaBuilder(BADMINTON)
            .withSpent(100)
            .build();
        assertTrue(BADMINTON.equals(editedBadminton));

        // different outstanding -> return true
        editedBasketball = new CcaBuilder(BASKETBALL)
            .withOutstanding(100)
            .build();
        assertTrue(BASKETBALL.equals(editedBasketball));

        // diferrent transaction -> true
        editedFloorball = new CcaBuilder(FLOORBALL)
            .withTransaction(TRANSACTION_4_ENTRIES)
            .build();
        assertTrue(FLOORBALL.equals(editedFloorball));
    }

    @Test
    public void getCcaName() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getCcaName().equals("JCRC"));
        assertFalse((cca.getCcaName().equals("Basketball"))); // different cca name
    }

    @Test
    public void getName() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getName().equals(new CcaName("JCRC")));
        assertFalse(cca.getName().equals(new CcaName("Basketball"))); // different CcaName
    }

    @Test
    public void getHeadName() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getHeadName().equals(CARL.getName().fullName));
        assertFalse((cca.getHeadName().equals(ALICE.getName().fullName))); // different head name
    }

    @Test
    public void getHead() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getHead().equals(CARL.getName()));
        assertFalse(cca.getHead().equals(ALICE.getName())); // different Name object for head
    }

    @Test
    public void getViceHeadName() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getViceHeadName().equals(DANIEL.getName().fullName));
        assertFalse((cca.getViceHeadName().equals(ALICE.getName().fullName))); // different vice head name
    }

    @Test
    public void getViceHead() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getViceHead().equals(DANIEL.getName()));
        assertFalse(cca.getViceHead().equals(ALICE.getName())); // different Name object for vice head
    }

    @Test
    public void getBudgetAmount() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getBudgetAmount().equals(500));
        assertFalse(cca.getBudgetAmount().equals("500")); // string value of the budget amount
        assertFalse(cca.getBudgetAmount().equals(200)); // different budget amount
    }

    @Test
    public void getBudget() {
        Cca cca = new CcaBuilder().build();
        assertTrue(cca.getBudget().equals(new Budget(500)));
        assertFalse(cca.getBudget().equals(new Budget(200))); // different budget amount object
    }
}
