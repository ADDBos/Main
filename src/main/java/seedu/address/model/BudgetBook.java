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

//@@author ericyjw
/**
 * Wraps all cca data at the budget-book level
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
     *
     * @param toBeCopied a {@code ReadOnlyBudgetBook} with a unique list of Cca
     */
    public BudgetBook(ReadOnlyBudgetBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code BudgetBook} with {@code newData}.
     *
     * @param newData a {@code ReadOnlyBudgetBook} with a unique list of Cca
     */
    public void resetData(ReadOnlyBudgetBook newData) {
        requireNonNull(newData);

        setCcas(newData.getCcaList());
    }

    /**
     * Replaces the contents of the unique Cca list with {@code Ccas}.
     *
     * @param ccas a list of Cca that does not have duplicate Ccas
     */
    public void setCcas(List<Cca> ccas) {
        this.ccas.setCcas(ccas);
    }

    //// Cca-level operations

    /**
     * Returns true if a Cca {@code tag} exists in the budget book.
     * Used to check if the {@code person}'s cca are in the {@code UniqueCcaList}.
     *
     * @param person a person to be added
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
     * Return true is a Cca name exists in the budget book.
     * Used to check if the {@code ccaName} is in the {@code UniqueCcaList}.
     * A weaker check as compared to checking the whole Cca.
     *
     * @param ccaName the name of the Cca to be checked
     */
    public boolean hasCca(CcaName ccaName) {
        requireNonNull(ccaName);
        return ccas.contains(ccaName.getNameOfCca());
    }

    /**
     * Returns true if a {@code cca} exists in the budget book.
     * Used to check is the {@code cca} is in the {@code UniqueCcaList}.
     *
     * @param cca the Cca to be checked
     */
    public boolean hasCca(Cca cca) {
        requireNonNull(cca);
        return ccas.contains(cca);
    }

    /**
     * Adds a Cca to the {@code UniqueCcaList}.
     * The {@code cca} must not already exist in the {@code UniqueCcaList}, in the budget book.
     *
     * @param cca a valid cca to be added
     */
    public void addCca(Cca cca) {
        ccas.add(cca);
    }

    /**
     * Replaces the given Cca {@code target} in the list with {@code editedCca}.
     * {@code target} must exist in the {@code UniqueCcaList}, in the Budget Book.
     * The Cca {@code editedCCA} must not be the same as another existing Cca in the budget book.
     *
     * @param target the cca to be replaced
     * @param editedCca the cca with the updated information
     */
    public void updateCca(Cca target, Cca editedCca) {
        requireNonNull(editedCca);

        ccas.setCca(target, editedCca);
    }

    /**
     * Removes {@code cca} from this {@code UniqueCcaList}.
     * {@code cca} must exist in the {@code UniqueCcaList}, in the budget book.
     *
     * @param cca the cca to be removed
     */
    public void removeCca(Cca cca) {
        ccas.remove(cca);
    }

    //// util methods
    @Override
    public String toString() {
        return ccas.asUnmodifiableObservableList().size() + " CCAs";
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
