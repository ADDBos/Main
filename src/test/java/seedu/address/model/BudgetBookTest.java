package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalCcas.FLOORBALL;
import static seedu.address.testutil.TypicalCcas.TRACK;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.exceptions.DuplicateCcaException;
import seedu.address.model.person.Person;
import seedu.address.testutil.CcaBuilder;

//@@author ericyjw
public class BudgetBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final BudgetBook budgetBook = new BudgetBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), budgetBook.getCcaList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyBudgetBook_replacesData() {
        BudgetBook newData = getTypicalBudgetBook();
        budgetBook.resetData(newData);
        assertEquals(newData, budgetBook);
    }

    @Test
    public void resetData_withDuplicateCcas_throwsDuplicateCcaException() {
        // Two Ccas with the same name
        Cca editedTrack = new CcaBuilder()
            .withCcaName("Track")
            .build();
        List<Cca> newCcas = Arrays.asList(TRACK, editedTrack);
        BudgetBookStub newData = new BudgetBookStub(newCcas);

        thrown.expect(DuplicateCcaException.class);
        budgetBook.resetData(newData);
    }

    @Test
    public void hasCca_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetBook.hasCca((Person) null);
    }

    @Test
    public void hasCca_nullCcaName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetBook.hasCca((CcaName) null);
    }

    @Test
    public void hasCca_ccaNotInBudgetBook_returnsFalse() {
        assertFalse(budgetBook.hasCca(TRACK));
    }

    @Test
    public void hasCca_ccaInBudgetBook_returnsTrue() {
        budgetBook.addCca(BASKETBALL);
        assertTrue(budgetBook.hasCca(BASKETBALL));
    }

    @Test
    public void hasCca_ccaWithSameIdentityFieldsInBudgetBook_returnsTrue() {
        budgetBook.addCca(FLOORBALL);
        Cca editedFloorball = new CcaBuilder(FLOORBALL)
            .withBudget(500)
            .withHead("Peter")
            .build();
        assertTrue(budgetBook.hasCca(editedFloorball));
    }

    @Test
    public void getCcaList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        budgetBook.getCcaList().remove(0);
    }

    /**
     * A stub ReadOnlyBudgetBook whose ccas list can violate interface constraints.
     */
    private static class BudgetBookStub implements ReadOnlyBudgetBook {
        private final ObservableList<Cca> ccas = FXCollections.observableArrayList();

        BudgetBookStub(Collection<Cca> ccas) {
            this.ccas.setAll(ccas);
        }

        @Override
        public ObservableList<Cca> getCcaList() {
            return ccas;
        }
    }

}
