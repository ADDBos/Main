package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.UniqueCcaList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Wraps all budget data at the budget-book level
 * Duplicates in CCA tags are not allowed (by .isSameCCA comparison)
 *
 * @author ericyjw
 */
public class BudgetBook implements ReadOnlyBudgetBook {
    private final UniqueCcaList ccas;

    {
        ccas = new UniqueCcaList();
    }

    public BudgetBook() {
    }

    /**
     * Creates a Budget Book using the CCAs in the {@code toBeCopied}
     */
    public BudgetBook(ReadOnlyBudgetBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code BudgetBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBudgetBook newData) {
        requireNonNull(newData);

        setCcas(newData.getCcaList());
    }

    /**
     * Replaces the contents of the CCA list with {@code CCAs}.
     * {@code CCAs} must not contain duplicate CCAs.
     */
    public void setCcas(List<Cca> ccas) {
        this.ccas.setCcas(ccas);
    }

    //// CCA-level operations

    /**
     * Returns true if a CCA {@code tag} exists in the Budget Book.
     */
    public boolean hasCca(Person person) {
        requireNonNull(person);
        Set<Tag> tagSet = person.getTags();
        for (Tag tag : tagSet) {
            String ccaName = tag.tagName;
            if (ccas.contains(ccaName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true is a CCA name exists in the Budget Book
     */
    public boolean hasCca(CcaName ccaName) {
        requireNonNull(ccaName);
        return ccas.contains(ccaName.toString());
    }

    /**
     * Returns true if a CCA {@code tag} exists in the Budget Book.
     */
    public boolean hasCca(Cca cca) {
        requireNonNull(cca);
        return ccas.contains(cca);
    }

    /**
     * Adds a CCA to the Budget Book.
     * The CCA must not already exist in the Budget Book.
     */
    public void addCca(Cca cca) {
        ccas.add(cca);
    }

    /**
     * Replaces the given CCA {@code target} in the list with {@code editedCca}.
     * {@code target} must exist in the Budget Book.
     * The CCA {@code editedCCA} must not be the same as another existing CCA in the Budget Book.
     */
    public void updateCca(Cca target, Cca editedCca) {
        requireNonNull(editedCca);

        ccas.setCca(target, editedCca);
    }

    /**
     * Removes {@code cca} from this {@code BudgetBook}.
     * {@code cca} must exist in the Budget book.
     */
    public void removeCca(Cca key) {
        ccas.remove(key);
    }

    //// util methods
    @Override
    public String toString() {
        return ccas.asUnmodifiableObservableList().size() + " CCAs";
        // TODO: refine later
    }

    @Override
    public ObservableList<Cca> getCcaList() {
        return ccas.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetBook // instanceof handles nulls
            && ccas.equals(((BudgetBook) other).ccas));
    }

    @Override
    public int hashCode() {
        return ccas.hashCode();
    }
}
